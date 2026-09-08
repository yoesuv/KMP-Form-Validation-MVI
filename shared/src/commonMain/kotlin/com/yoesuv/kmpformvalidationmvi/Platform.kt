package com.yoesuv.kmpformvalidationmvi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform