package com.yoesuv.kmpformvalidationmvi.feature.login

data class LoginState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false
)
