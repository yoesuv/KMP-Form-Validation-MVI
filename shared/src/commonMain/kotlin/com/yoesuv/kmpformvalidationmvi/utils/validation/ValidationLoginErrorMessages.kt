package com.yoesuv.kmpformvalidationmvi.utils.validation

data class ValidationLoginErrorMessages(
    val emailRequired: String,
    val emailInvalid: String,
    val passwordRequired: String,
    val passwordTooShort: String
)
