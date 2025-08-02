package com.example.boo_android.presentation.main.aichat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.boo_android.AiComponent
import com.example.boo_android.AppNavigationItem
import com.example.boo_android.R
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.request.SendChatRequest
import com.example.boo_android.data.request.StartChatRequest
import com.example.boo_android.data.response.AiListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun AiChatScreen(
    navController: NavController
) {
    var aiList by remember { mutableStateOf<List<AiListResponse>>(emptyList()) }
    var aiId = ""

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                ApiProvider.chatApi.fetchAiList()
            }.onSuccess {
                aiList = it
                Log.d("TEST", aiList.first { it.name == "강무진" }.id.toString())
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF192432), // 위쪽 컬러
                        Color(0xFF060D23)  // 아래쪽 컬러
                    )
                )
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.padding(top = 28.dp),
            painter = painterResource(R.drawable.ic_question),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = """
                        오늘 무서운 일이 있었군요.
                어떤 AI와 얘기하며 털어 놓으시겠어요?
                    """.trimIndent(),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        

        // aiList를 사용하여 동적으로 AiComponent 생성
        aiList.forEach { ai ->
            val iconResId = when (ai.name) {
                "꼬마 유령 Boo!" -> R.drawable.ic_ai_1
                "강무진" -> R.drawable.ic_ai_2
                "책벌레 엘리" -> R.drawable.ic_ai_3
                "낭랑 18세 쏘쏘" -> R.drawable.ic_ai_4
                else -> R.drawable.ic_ai_1 // 기본 아이콘
            }
            AiComponent(
                icon = iconResId,
                title = ai.name,
                content = ai.description,
                onClick = {
                    navController.navigate(AppNavigationItem.AiChatDetail.route + "/${ai.id}/${ai.name}")
                }
            )
        }
    }
}


suspend fun server(
    aiUUID: UUID,
    aiId: String,
) : String {
    return withContext(Dispatchers.IO) {
        kotlin.runCatching {
            ApiProvider.chatApi.startChat(
                StartChatRequest(
                    aiId = aiUUID
                )
            )
        }.fold(
            onSuccess = {
                val fullString = it.toString()
                Log.d("server_response", "Full startChat response: $fullString")
                fullString // 응답이 이미 UUID 문자열이므로 바로 반환
            },
            onFailure = {
                Log.e("server", "Error in server function: ${it.message}")
                aiId // 실패 시 기존 aiId 반환 또는 다른 오류 처리
            }
        )
    }
}