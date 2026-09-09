package com.yoesuv.kmpformvalidationmvi.utils.validation

/**
 * Extension function to validate email format with string parameters
 * @param emailRequiredMessage Message to show when email is required
 * @param emailInvalidMessage Message to show when email format is invalid
 * @return ValidationModel with validation result and localized message
 */
fun String.validateEmail(
    emailRequiredMessage: String,
    emailInvalidMessage: String,
): ValidationModel =
    when {
        this.isBlank() -> {
            ValidationModel(
                isValid = false,
                message = emailRequiredMessage,
            )
        }

        !this.isValidEmailFormat() -> {
            ValidationModel(
                isValid = false,
                message = emailInvalidMessage,
            )
        }

        else -> {
            ValidationModel(
                isValid = true,
                message = "",
            )
        }
    }

/**
 * Helper function to check email format using regex
 * @return true if email format is valid, false otherwise
 */
private fun String.isValidEmailFormat(): Boolean {
    val emailRegex =
        Regex(
            pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        )
    return emailRegex.matches(this)
}
