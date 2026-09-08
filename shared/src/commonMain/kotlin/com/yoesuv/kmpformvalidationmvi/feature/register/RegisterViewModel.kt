package com.yoesuv.kmpformvalidationmvi.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.kmpformvalidationmvi.utils.validation.ValidationRegisterErrorMessages
import com.yoesuv.kmpformvalidationmvi.utils.validation.validateConfirmPassword
import com.yoesuv.kmpformvalidationmvi.utils.validation.validateEmail
import com.yoesuv.kmpformvalidationmvi.utils.validation.validateFullName
import com.yoesuv.kmpformvalidationmvi.utils.validation.validatePassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val errorMessages: ValidationRegisterErrorMessages
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events.asSharedFlow()

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.FullNameChanged -> handleFullNameChanged(intent.fullName)
            is RegisterIntent.EmailChanged -> handleEmailChanged(intent.email)
            is RegisterIntent.PasswordChanged -> handlePasswordChanged(intent.password)
            is RegisterIntent.ConfirmPasswordChanged -> handleConfirmPasswordChanged(intent.confirmPassword)
            is RegisterIntent.Submit -> handleSubmit()
        }
    }

    private fun handleFullNameChanged(fullName: String) {
        val fullNameValidation = fullName.validateFullName(
            errorMessages.fullNameRequired,
            errorMessages.fullNameTooShort
        )
        _state.update { currentState ->
            currentState.copy(
                fullName = fullName,
                fullNameError = if (fullNameValidation.isValid) null else fullNameValidation.message,
                isFormValid = checkFormValidity(
                    fullName = fullName,
                    email = currentState.email,
                    password = currentState.password,
                    confirmPassword = currentState.confirmPassword
                )
            )
        }
    }

    private fun handleEmailChanged(email: String) {
        val emailValidation =
            email.validateEmail(errorMessages.emailRequired, errorMessages.emailInvalid)
        _state.update { currentState ->
            currentState.copy(
                email = email,
                emailError = if (emailValidation.isValid) null else emailValidation.message,
                isFormValid = checkFormValidity(
                    fullName = currentState.fullName,
                    email = email,
                    password = currentState.password,
                    confirmPassword = currentState.confirmPassword
                )
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val passwordValidation = password.validatePassword(
            errorMessages.passwordRequired,
            errorMessages.passwordTooShort
        )
        val currentState = _state.value
        val confirmPasswordValidation = currentState.confirmPassword.validateConfirmPassword(
            originalPassword = password,
            confirmPasswordRequiredMessage = errorMessages.confirmPasswordRequired,
            passwordsDoNotMatchMessage = errorMessages.passwordsDoNotMatch
        )

        _state.update {
            it.copy(
                password = password,
                passwordError = if (passwordValidation.isValid) null else passwordValidation.message,
                confirmPasswordError = if (currentState.confirmPassword.isNotEmpty()) {
                    if (confirmPasswordValidation.isValid) null else confirmPasswordValidation.message
                } else null,
                isFormValid = checkFormValidity(
                    fullName = currentState.fullName,
                    email = currentState.email,
                    password = password,
                    confirmPassword = currentState.confirmPassword
                )
            )
        }
    }

    private fun handleConfirmPasswordChanged(confirmPassword: String) {
        val currentState = _state.value
        val confirmPasswordValidation = confirmPassword.validateConfirmPassword(
            originalPassword = currentState.password,
            confirmPasswordRequiredMessage = errorMessages.confirmPasswordRequired,
            passwordsDoNotMatchMessage = errorMessages.passwordsDoNotMatch
        )

        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = if (confirmPasswordValidation.isValid) null else confirmPasswordValidation.message,
                isFormValid = checkFormValidity(
                    fullName = currentState.fullName,
                    email = currentState.email,
                    password = currentState.password,
                    confirmPassword = confirmPassword
                )
            )
        }
    }

    private fun handleSubmit() {
        val currentState = _state.value

        if (currentState.isLoading) return

        val fullNameValidation = currentState.fullName.validateFullName(
            errorMessages.fullNameRequired,
            errorMessages.fullNameTooShort
        )
        val emailValidation = currentState.email.validateEmail(
            errorMessages.emailRequired,
            errorMessages.emailInvalid
        )
        val passwordValidation = currentState.password.validatePassword(
            errorMessages.passwordRequired,
            errorMessages.passwordTooShort
        )
        val confirmPasswordValidation = currentState.confirmPassword.validateConfirmPassword(
            originalPassword = currentState.password,
            confirmPasswordRequiredMessage = errorMessages.confirmPasswordRequired,
            passwordsDoNotMatchMessage = errorMessages.passwordsDoNotMatch
        )

        if (!fullNameValidation.isValid || !emailValidation.isValid ||
            !passwordValidation.isValid || !confirmPasswordValidation.isValid
        ) {
            _state.update {
                it.copy(
                    fullNameError = if (fullNameValidation.isValid) null else fullNameValidation.message,
                    emailError = if (emailValidation.isValid) null else emailValidation.message,
                    passwordError = if (passwordValidation.isValid) null else passwordValidation.message,
                    confirmPasswordError = if (confirmPasswordValidation.isValid) null else confirmPasswordValidation.message
                )
            }
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(3_000)
            _state.update { it.copy(isLoading = false) }
            _events.emit("Register success")
        }
    }

    private fun checkFormValidity(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val fullNameValidation = fullName.validateFullName(
            errorMessages.fullNameRequired,
            errorMessages.fullNameTooShort
        )
        val emailValidation =
            email.validateEmail(errorMessages.emailRequired, errorMessages.emailInvalid)
        val passwordValidation = password.validatePassword(
            errorMessages.passwordRequired,
            errorMessages.passwordTooShort
        )
        val confirmPasswordValidation = confirmPassword.validateConfirmPassword(
            originalPassword = password,
            confirmPasswordRequiredMessage = errorMessages.confirmPasswordRequired,
            passwordsDoNotMatchMessage = errorMessages.passwordsDoNotMatch
        )

        return fullNameValidation.isValid &&
                emailValidation.isValid &&
                passwordValidation.isValid &&
                confirmPasswordValidation.isValid
    }
}
