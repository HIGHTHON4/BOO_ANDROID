package com.example.boo_android.presentation.main.aichat

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.request.StopChatRequest
import com.example.boo_android.data.response.StopChatResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("ImplicitSamInstance")
@Composable
fun AiChatFinishScreen(
    aiId: String,
    navController: NavController,
) {
    var finishData: StopChatResponse by remember { mutableStateOf(StopChatResponse(
        summary = UUID.randomUUID(),
        fearLevel = "",
        title = ""
    ))}
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                ApiProvider.chatApi.stopChat(
                    StopChatRequest(
                        reportId = aiId
                    )
                )
            }.onSuccess {
                finishData = it
                Log.d("TEST", it.toString())
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
            ),
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF232B3A))
                    .padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 제목
                Text(
                    text = "지하주차장의 정적",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 등급
                Text(
                    text = "B등급",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFB0C4DE)
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Boo! 의 한 마디 및 설명
                Text(
                    text = "Boo! 의 한 마디",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFE588)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "방 안 시계가 멈췄는데... 초침만 혼자 움직이고 있었다고 했지. 눈앞에선 아무 일도 없는데, 뭔가가 '계속되고 있었다'는 느낌... 나였어도 괜히 숨 멎혔을 것 같아.\n잔잔하지만 계속 생각나는 이야기라서, 오늘은 B등급 줄게!",
                    fontSize = 15.sp,
                    color = Color(0xFFD8DEE9)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // 유령 이모지
                Text(
                    text = "\uD83D\uDC7B", // 👻
                    fontSize = 38.sp
                )
                Spacer(modifier = Modifier.height(22.dp))
                // 버튼 2개
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF344359)
                        )
                    ) {
                        Text("채팅 돌아보기")
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF449AFF)
                        )
                    ) {
                        Text("새 채팅 진행하기")
                    }
                }
            }
        }
    }
}