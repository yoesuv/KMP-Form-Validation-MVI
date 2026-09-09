package com.yoesuv.kmpformvalidationmvi.feature.register

sealed class RegisterIntent {
    data class FullNameChanged(
        val fullName: String,
    ) : RegisterIntent()

    data class EmailChanged(
        val email: String,
    ) : RegisterIntent()

    data class PasswordChanged(
        val password: String,
    ) : RegisterIntent()

    data class ConfirmPasswordChanged(
        val confirmPassword: String,
    ) : RegisterIntent()

    data object Submit : RegisterIntent()
}
