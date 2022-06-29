package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.*
import java.util.stream.Collectors

import java.util.stream.StreamSupport




@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
internal class BookRepositoryJdbcTests {
    @Autowired
    private val bookRepository: BookRepository? = null

    @Autowired
    private val jdbcAggregateTemplate: JdbcAggregateTemplate? = null

    @Test
    fun findAllBooks() {
        val book1: Book = createBook("1234561235", "Title", "Author", 12.90)
        val book2: Book = createBook("1234561236", "Another Title", "Author", 12.90)
        jdbcAggregateTemplate!!.insert<Any>(book1)
        jdbcAggregateTemplate.insert<Any>(book2)
        val actualBooks = bookRepository!!.findAll()
        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
            .filter { book: Book ->
                book.isbn.equals(book1.isbn) || book.isbn.equals(book2.isbn)
            }
            .collect(Collectors.toList())).hasSize(2)
    }

    @Test
    fun findBookByIsbnWhenExisting() {
        val bookIsbn = "1234561237"
        val book: Book = createBook(bookIsbn, "Title", "Author", 12.90)
        jdbcAggregateTemplate!!.insert<Book>(book)
        val actualBook: Optional<Book> = bookRepository!!.findByIsbn(bookIsbn)
        assertThat(actualBook).isPresent
        assertThat(actualBook.get().isbn).isEqualTo(book.isbn)
    }
}
