package com.yoesuv.kmpformvalidationmvi.utils.validation

data class ValidationRegisterErrorMessages(
    val fullNameRequired: String,
    val fullNameTooShort: String,
    val emailRequired: String,
    val emailInvalid: String,
    val passwordRequired: String,
    val passwordTooShort: String,
    val confirmPasswordRequired: String,
    val passwordsDoNotMatch: String
)