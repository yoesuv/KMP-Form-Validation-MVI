package com.yoesuv.kmpformvalidationmvi.utils.validation

import androidx.compose.runtime.Composable
import kmpformvalidationmvi.composeapp.generated.resources.Res
import kmpformvalidationmvi.composeapp.generated.resources.confirm_password_required
import kmpformvalidationmvi.composeapp.generated.resources.passwords_do_not_match
import org.jetbrains.compose.resources.stringResource

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
    passwordsDoNotMatchMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = confirmPasswordRequiredMessage
        )

        this != originalPassword -> ValidationModel(
            isValid = false,
            message = passwordsDoNotMatchMessage
        )

        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Composable extension function to validate confirm password with compose string resources
 * @param originalPassword The original password to compare against
 * @return ValidationModel with validation result and localized message
 */
@Composable
fun String.validateConfirmPasswordComposable(originalPassword: String): ValidationModel {
    return this.validateConfirmPassword(
        originalPassword = originalPassword,
        confirmPasswordRequiredMessage = stringResource(Res.string.confirm_password_required),
        passwordsDoNotMatchMessage = stringResource(Res.string.passwords_do_not_match)
    )
}