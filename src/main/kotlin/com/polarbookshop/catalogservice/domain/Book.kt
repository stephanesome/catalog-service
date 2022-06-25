package com.polarbookshop.catalogservice.domain

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

data class Book(@NotBlank(message = "The book ISBN must be defined.")
                @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
                val isbn: String ,

                @NotBlank(message = "The book title must be defined.")
                val title: String,

                @NotBlank(message = "The book author must be defined.")
                val author: String,

                @NotNull(message = "The book price must be defined.")
                @Positive(message = "The book price must be greater than zero.")
                val price: Double){}
