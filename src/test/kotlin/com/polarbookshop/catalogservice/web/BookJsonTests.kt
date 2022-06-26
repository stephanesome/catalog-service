package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.createBook
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
class BookJsonTests() {

    @Autowired
    private lateinit var json: JacksonTester<Book>

    @Test
    @Throws(Exception::class)
    fun testSerialize() {
        val book = createBook("1234567890", "Title", "Author", 9.90)
        val jsonContent = json.write(book)
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn)
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title)
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author)
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price)
    }

    @Test
    @Throws(Exception::class)
    fun testDeserialize() {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                """
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(createBook("1234567890", "Title", "Author", 9.90));
    }

}
