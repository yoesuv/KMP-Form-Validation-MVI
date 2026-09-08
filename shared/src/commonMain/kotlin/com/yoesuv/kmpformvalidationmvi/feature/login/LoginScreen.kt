package com.yoesuv.kmpformvalidationmvi.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.yoesuv.kmpformvalidationmvi.utils.validation.ValidationLoginErrorMessages
import kmpformvalidationmvi.shared.generated.resources.Res
import kmpformvalidationmvi.shared.generated.resources.create_account_link
import kmpformvalidationmvi.shared.generated.resources.dont_have_account
import kmpformvalidationmvi.shared.generated.resources.email_invalid_format
import kmpformvalidationmvi.shared.generated.resources.email_label
import kmpformvalidationmvi.shared.generated.resources.email_placeholder
import kmpformvalidationmvi.shared.generated.resources.email_required
import kmpformvalidationmvi.shared.generated.resources.login_button
import kmpformvalidationmvi.shared.generated.resources.login_title
import kmpformvalidationmvi.shared.generated.resources.app_name
import kmpformvalidationmvi.shared.generated.resources.password_label
import kmpformvalidationmvi.shared.generated.resources.password_placeholder
import kmpformvalidationmvi.shared.generated.resources.password_required
import kmpformvalidationmvi.shared.generated.resources.password_too_short
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
) {
    val errorMessages = ValidationLoginErrorMessages(
        emailRequired = stringResource(Res.string.email_required),
        emailInvalid = stringResource(Res.string.email_invalid_format),
        passwordRequired = stringResource(Res.string.password_required),
        passwordTooShort = stringResource(Res.string.password_too_short)
    )

    val viewModel: LoginViewModel = viewModel {
        LoginViewModel(errorMessages)
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
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = stringResource(Res.string.login_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email Field with Validation
                AppTextField(
                    value = state.email,
                    onValueChange = { newEmail ->
                        viewModel.onIntent(LoginIntent.EmailChanged(newEmail))
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
                        viewModel.onIntent(LoginIntent.PasswordChanged(newPassword))
                    },
                    label = stringResource(Res.string.password_label),
                    placeholder = stringResource(Res.string.password_placeholder),
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                AppButton(
                    text = stringResource(Res.string.login_button),
                    onClick = {
                        viewModel.onIntent(LoginIntent.Submit)
                    },
                    fillMaxWidth = true,
                    isLoading = state.isLoading,
                    enabled = state.isFormValid && !state.isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation to Register
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.dont_have_account),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    TextButton(
                        onClick = onNavigateToRegister
                    ) {
                        Text(
                            text = stringResource(Res.string.create_account_link),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
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