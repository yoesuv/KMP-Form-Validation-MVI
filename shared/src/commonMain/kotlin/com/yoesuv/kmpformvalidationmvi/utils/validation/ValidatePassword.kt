package com.yoesuv.kmpformvalidationmvi.utils.validation

/**
 * Extension function to validate password with string parameters
 * @param passwordRequiredMessage Message to show when password is required
 * @param passwordTooShortMessage Message to show when password is too short
 * @return ValidationModel with validation result and localized message
 */
fun String.validatePassword(
    passwordRequiredMessage: String,
    passwordTooShortMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = passwordRequiredMessage
        )

        this.length < 5 -> ValidationModel(
            isValid = false,
            message = passwordTooShortMessage
        )

        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}