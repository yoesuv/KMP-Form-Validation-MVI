package com.yoesuv.kmpformvalidationmvi

import androidx.compose.ui.window.ComposeUIViewController

// iOS entry point must keep UpperCamelCase name to match Kotlin/Native export.
@Suppress("ktlint:standard:function-naming")
fun MainViewController() = ComposeUIViewController { App() }
