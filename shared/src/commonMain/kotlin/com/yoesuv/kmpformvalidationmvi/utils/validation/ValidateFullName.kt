package com.yoesuv.kmpformvalidationmvi.utils.validation

/**
 * Extension function to validate full name with string parameters
 * @param fullNameRequiredMessage Message to show when full name is required
 * @param fullNameTooShortMessage Message to show when full name is too short
 * @return ValidationModel with validation result and localized message
 */
fun String.validateFullName(
    fullNameRequiredMessage: String,
    fullNameTooShortMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = fullNameRequiredMessage
        )

        this.trim().length < 2 -> ValidationModel(
            isValid = false,
            message = fullNameTooShortMessage
        )

        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}