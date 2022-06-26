package com.polarbookshop.catalogservice.domain

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

class Book {
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    var isbn: String? = null

    @NotBlank(message = "The book title must be defined.")
    var title: String? = null

    @NotBlank(message = "The book author must be defined.")
    var author: String? = null

    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    var price: Double? = null
}

fun createBook(isbn: String?, title: String?, author: String?, price: Double?): Book {
    val book = Book()
    book.isbn = isbn
    book.title = title
    book.author = author
    book.price = price
    return book
}
