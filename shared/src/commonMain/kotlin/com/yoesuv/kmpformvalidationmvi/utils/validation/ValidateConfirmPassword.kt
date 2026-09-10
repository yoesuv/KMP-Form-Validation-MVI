package com.yoesuv.kmpformvalidationmvi.utils.validation

/**
 * Extension function to validate confirm password with string parameters
 * @param originalPassword The original password to compare against
 * @param confirmPasswordRequiredMessage Message to show when confirm password is required
 * @param passwordsDoNotMatchMessage Message to show when passwords don't match
 * @return ValidationModel with validation result and localized message
 */
fun String.validateConfirmPassword(
    originalPassword: String,
    confirmPasswordRequiredMessage: String,
    passwordsDoNotMatchMessage: String,
): ValidationModel =
    when {
        this.isBlank() -> {
            ValidationModel(
                isValid = false,
                message = confirmPasswordRequiredMessage,
            )
        }

        this != originalPassword -> {
            ValidationModel(
                isValid = false,
                message = passwordsDoNotMatchMessage,
            )
        }

        else -> {
            ValidationModel(
                isValid = true,
                message = "",
            )
        }
    }
