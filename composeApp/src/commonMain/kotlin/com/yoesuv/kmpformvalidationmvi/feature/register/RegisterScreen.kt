package com.yoesuv.kmpformvalidationmvi.feature.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoesuv.kmpformvalidationmvi.feature.components.AppButton
import com.yoesuv.kmpformvalidationmvi.feature.components.AppPasswordField
import com.yoesuv.kmpformvalidationmvi.feature.components.AppTextField
import com.yoesuv.kmpformvalidationmvi.utils.validation.ValidationRegisterErrorMessages
import kmpformvalidationmvi.composeapp.generated.resources.Res
import kmpformvalidationmvi.composeapp.generated.resources.already_have_account
import kmpformvalidationmvi.composeapp.generated.resources.confirm_password_label
import kmpformvalidationmvi.composeapp.generated.resources.confirm_password_placeholder
import kmpformvalidationmvi.composeapp.generated.resources.confirm_password_required
import kmpformvalidationmvi.composeapp.generated.resources.email_invalid_format
import kmpformvalidationmvi.composeapp.generated.resources.email_label
import kmpformvalidationmvi.composeapp.generated.resources.email_placeholder
import kmpformvalidationmvi.composeapp.generated.resources.email_required
import kmpformvalidationmvi.composeapp.generated.resources.full_name_label
import kmpformvalidationmvi.composeapp.generated.resources.full_name_placeholder
import kmpformvalidationmvi.composeapp.generated.resources.full_name_required
import kmpformvalidationmvi.composeapp.generated.resources.full_name_too_short
import kmpformvalidationmvi.composeapp.generated.resources.login_link
import kmpformvalidationmvi.composeapp.generated.resources.password_label
import kmpformvalidationmvi.composeapp.generated.resources.password_placeholder
import kmpformvalidationmvi.composeapp.generated.resources.password_required
import kmpformvalidationmvi.composeapp.generated.resources.password_too_short
import kmpformvalidationmvi.composeapp.generated.resources.passwords_do_not_match
import kmpformvalidationmvi.composeapp.generated.resources.register_button
import kmpformvalidationmvi.composeapp.generated.resources.register_title
import kmpformvalidationmvi.composeapp.generated.resources.app_name
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
) {
    val errorMessages = ValidationRegisterErrorMessages(
        fullNameRequired = stringResource(Res.string.full_name_required),
        fullNameTooShort = stringResource(Res.string.full_name_too_short),
        emailRequired = stringResource(Res.string.email_required),
        emailInvalid = stringResource(Res.string.email_invalid_format),
        passwordRequired = stringResource(Res.string.password_required),
        passwordTooShort = stringResource(Res.string.password_too_short),
        confirmPasswordRequired = stringResource(Res.string.confirm_password_required),
        passwordsDoNotMatch = stringResource(Res.string.passwords_do_not_match)
    )

    val viewModel: RegisterViewModel = viewModel {
        RegisterViewModel(errorMessages)
    }
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Register Title
                Text(
                    text = stringResource(Res.string.register_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Full Name Field
                AppTextField(
                    value = state.fullName,
                    onValueChange = { newFullName ->
                        viewModel.onIntent(RegisterIntent.FullNameChanged(newFullName))
                    },
                    label = stringResource(Res.string.full_name_label),
                    placeholder = stringResource(Res.string.full_name_placeholder),
                    keyboardType = KeyboardType.Text,
                    isError = state.fullNameError != null,
                    errorMessage = state.fullNameError
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                AppTextField(
                    value = state.email,
                    onValueChange = { newEmail ->
                        viewModel.onIntent(RegisterIntent.EmailChanged(newEmail))
                    },
                    label = stringResource(Res.string.email_label),
                    placeholder = stringResource(Res.string.email_placeholder),
                    keyboardType = KeyboardType.Email,
                    isError = state.emailError != null,
                    errorMessage = state.emailError
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                AppPasswordField(
                    value = state.password,
                    onValueChange = { newPassword ->
                        viewModel.onIntent(RegisterIntent.PasswordChanged(newPassword))
                    },
                    label = stringResource(Res.string.password_label),
                    placeholder = stringResource(Res.string.password_placeholder),
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                AppPasswordField(
                    value = state.confirmPassword,
                    onValueChange = { newConfirmPassword ->
                        viewModel.onIntent(RegisterIntent.ConfirmPasswordChanged(newConfirmPassword))
                    },
                    label = stringResource(Res.string.confirm_password_label),
                    placeholder = stringResource(Res.string.confirm_password_placeholder),
                    isError = state.confirmPasswordError != null,
                    errorMessage = state.confirmPasswordError
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Register Button
                AppButton(
                    text = stringResource(Res.string.register_button),
                    onClick = {
                        viewModel.onIntent(RegisterIntent.Submit)
                    },
                    enabled = state.isFormValid && !state.isLoading,
                    isLoading = state.isLoading,
                    fillMaxWidth = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Link
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.already_have_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(
                        onClick = onNavigateBack
                    ) {
                        Text(
                            text = stringResource(Res.string.login_link),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            Text(
                text = stringResource(Res.string.app_name),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}