package com.example.boo_android

sealed class AppNavigationItem(val route: String) {
    object SignIn : AppNavigationItem("signin")
    object SignUp : AppNavigationItem("signup")
    object Main : AppNavigationItem("main")
    object AiChat : AppNavigationItem("ai_chat")
    object AiChatDetail : AppNavigationItem("aichatdetail")
    object AiChatFinish : AppNavigationItem("ai_chat_finish")
    object StrangeSend : AppNavigationItem("strange_send")
    object StrangeSendDetail : AppNavigationItem("strange_send_detail")
    object History : AppNavigationItem("history")
    object HistoryDetail : AppNavigationItem("history_detail")
}