package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {
    @Mock
    private val bookRepository: BookRepository? = null

    @InjectMocks
    private val bookService: BookService? = null

    @Test
    fun whenBookToCreateAlreadyExistsThenThrows() {
        val bookIsbn = "1234561232"
        val bookToCreate = createBook(bookIsbn, "Title", "Author", 9.90, "Polarsophia")
        `when`(bookRepository!!.existsByIsbn(bookIsbn)).thenReturn(true)
        assertThatThrownBy { bookService!!.addBookToCatalog(bookToCreate) }
            .isInstanceOf(BookAlreadyExistsException::class.java)
            .hasMessage("A book with ISBN $bookIsbn already exists.")
    }

    @Test
    fun whenBookToReadDoesNotExistThenThrows() {
        val bookIsbn = "1234561232"
        `when`(bookRepository!!.findByIsbn(bookIsbn)).thenReturn(Optional.empty())
        assertThatThrownBy { bookService!!.viewBookDetails(bookIsbn) }
            .isInstanceOf(BookNotFoundException::class.java)
            .hasMessage("The book with ISBN $bookIsbn was not found.")
    }
}
