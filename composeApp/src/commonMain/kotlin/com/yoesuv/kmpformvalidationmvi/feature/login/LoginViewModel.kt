package com.yoesuv.kmpformvalidationmvi.feature.login

import androidx.lifecycle.ViewModel
import com.yoesuv.kmpformvalidationmvi.utils.validation.validateEmail
import com.yoesuv.kmpformvalidationmvi.utils.validation.validatePassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val emailRequiredMessage: String,
    private val emailInvalidMessage: String,
    private val passwordRequiredMessage: String,
    private val passwordTooShortMessage: String
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> handleEmailChanged(intent.email)
            is LoginIntent.PasswordChanged -> handlePasswordChanged(intent.password)
            is LoginIntent.Submit -> handleSubmit()
        }
    }

    private fun handleEmailChanged(email: String) {
        val emailValidation = email.validateEmail(emailRequiredMessage, emailInvalidMessage)
        _state.update { currentState ->
            currentState.copy(
                email = email,
                emailError = if (emailValidation.isValid) null else emailValidation.message,
                isFormValid = checkFormValidity(email, currentState.password)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val passwordValidation = password.validatePassword(passwordRequiredMessage, passwordTooShortMessage)
        _state.update { currentState ->
            currentState.copy(
                password = password,
                passwordError = if (passwordValidation.isValid) null else passwordValidation.message,
                isFormValid = checkFormValidity(currentState.email, password)
            )
        }
    }

    private fun handleSubmit() {
        val currentState = _state.value

        val emailValidation = currentState.email.validateEmail(emailRequiredMessage, emailInvalidMessage)
        val passwordValidation = currentState.password.validatePassword(passwordRequiredMessage, passwordTooShortMessage)

        if (!emailValidation.isValid || !passwordValidation.isValid) {
            _state.update {
                it.copy(
                    emailError = if (emailValidation.isValid) null else emailValidation.message,
                    passwordError = if (passwordValidation.isValid) null else passwordValidation.message
                )
            }
            return
        }

        _state.update { it.copy(isLoading = true) }
        // TODO: Implement actual login logic here
    }

    private fun checkFormValidity(email: String, password: String): Boolean {
        val emailValidation = email.validateEmail(emailRequiredMessage, emailInvalidMessage)
        val passwordValidation = password.validatePassword(passwordRequiredMessage, passwordTooShortMessage)
        return emailValidation.isValid && passwordValidation.isValid
    }
}
