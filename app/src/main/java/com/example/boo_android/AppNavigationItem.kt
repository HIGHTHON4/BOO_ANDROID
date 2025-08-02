package com.example.boo_android

sealed class AppNavigationItem(val route: String) {
    object SignIn : AppNavigationItem("signin")
    object SignUp : AppNavigationItem("signup")
    object Main : AppNavigationItem("main")
    object AiChat : AppNavigationItem("ai_chat")
    object AiChatDetail : AppNavigationItem("aichatdetail")
    object AiChatFinish : AppNavigationItem("aichatfinish")
}