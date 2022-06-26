package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookNotFoundException
import com.polarbookshop.catalogservice.domain.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(BookController::class)
internal class BookControllerMvcTests {
    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val bookService: BookService? = null

    @Test
    @Throws(Exception::class)
    fun whenGetBookNotExistingThenShouldReturn404() {
        val isbn = "73737313940"
        given(bookService!!.viewBookDetails(isbn)).willThrow(BookNotFoundException::class.java)
        mockMvc
            ?.perform(get("/books/$isbn"))
            ?.andExpect(status().isNotFound)
    }
}
