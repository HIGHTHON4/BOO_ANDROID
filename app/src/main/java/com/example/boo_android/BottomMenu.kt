package com.example.boo_android

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomMenu(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    AI_CHAT("ai_chat", R.string.ai_chat, Icons.AutoMirrored.Filled.Chat),
    HISTORY("history", R.string.history, Icons.Filled.History),
    STRANGE_SEND("strange_send", R.string.strange_send, Icons.AutoMirrored.Filled.Send),
}