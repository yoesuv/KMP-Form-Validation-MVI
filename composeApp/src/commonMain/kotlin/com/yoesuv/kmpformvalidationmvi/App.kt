package com.yoesuv.kmpformvalidationmvi

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoesuv.kmpformvalidationmvi.core.route.AppRoute
import com.yoesuv.kmpformvalidationmvi.feature.login.LoginScreen
import com.yoesuv.kmpformvalidationmvi.feature.register.RegisterScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoute.Login
        ) {
            composable<AppRoute.Login> {
                LoginScreen(onNavigateToRegister = {
                    navController.navigate(AppRoute.Register)
                })
            }
            composable<AppRoute.Register> {
                RegisterScreen()
            }
        }
    }
}