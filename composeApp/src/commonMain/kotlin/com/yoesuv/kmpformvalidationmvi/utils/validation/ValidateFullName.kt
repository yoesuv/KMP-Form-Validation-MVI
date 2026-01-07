package com.yoesuv.kmpformvalidationmvi.utils.validation

import androidx.compose.runtime.Composable
import kmpformvalidationmvi.composeapp.generated.resources.Res
import kmpformvalidationmvi.composeapp.generated.resources.full_name_required
import kmpformvalidationmvi.composeapp.generated.resources.full_name_too_short
import org.jetbrains.compose.resources.stringResource


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

/**
 * Composable extension function to validate full name with compose string resources
 * @return ValidationModel with validation result and localized message
 */
@Composable
fun String.validateFullNameComposable(): ValidationModel {
    return this.validateFullName(
        fullNameRequiredMessage = stringResource(Res.string.full_name_required),
        fullNameTooShortMessage = stringResource(Res.string.full_name_too_short)
    )
}