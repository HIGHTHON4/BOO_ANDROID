package com.example.boo_android

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomMenu(val route: String,  val title: String, val icon: Int) {
    AI_CHAT("ai_chat", "챗봇", R.drawable.ai_chat),
    HISTORY("history", "챗봇 기록", R.drawable.history),
    STRANGE_SEND("strange_send", "랜덤 괴담", R.drawable.strange_send),
}