package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BookValidationTests {
    private var validator: Validator? = null

    @BeforeAll
    fun setUp() {
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds() {
        val book = createBook("1234567890", "Title", "Author", 9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).isEmpty()
    }

    @Test
    fun whenIsbnNotDefinedThenValidationFails() {
        val book = createBook("", "Title", "Author", 9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(2)
        val constraintViolationMessages = violations.stream()
            .map { obj: ConstraintViolation<Book> -> obj.message }
            .collect(Collectors.toList())
        assertThat(constraintViolationMessages)
            .contains("The book ISBN must be defined.")
            .contains("The ISBN format must be valid.")
    }

    @Test
    fun whenIsbnDefinedButIncorrectThenValidationFails() {
        val book = createBook("a234567890", "Title", "Author", 9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The ISBN format must be valid.")
    }

    @Test
    fun whenTitleIsNotDefinedThenValidationFails() {
        val book = createBook("1234567890", "", "Author", 9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book title must be defined.")
    }

    @Test
    fun whenAuthorIsNotDefinedThenValidationFails() {
        val book = createBook("1234567890", "Title", "", 9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book author must be defined.")
    }

   @Test
    fun whenPriceIsNotDefinedThenValidationFails() {
        val book = createBook("1234567890", "Title", "Author", null, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be defined.")
    }

    @Test
    fun whenPriceDefinedButZeroThenValidationFails() {
        val book = createBook("1234567890", "Title", "Author", 0.0, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be greater than zero.")
    }

    @Test
    fun whenPriceDefinedButNegativeThenValidationFails() {
        val book = createBook("1234567890", "Title", "Author", -9.90, "Polarsophia")
        val violations: Set<ConstraintViolation<Book>> = validator?.validate(book) as Set<ConstraintViolation<Book>>
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The book price must be greater than zero.")
    }
}
