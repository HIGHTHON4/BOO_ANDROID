package com.example.boo_android

sealed class AppNavigationItem(val route: String) {
    object SignIn : AppNavigationItem("signin")
    object SignUp : AppNavigationItem("signup")
    object Main : AppNavigationItem("main")
}