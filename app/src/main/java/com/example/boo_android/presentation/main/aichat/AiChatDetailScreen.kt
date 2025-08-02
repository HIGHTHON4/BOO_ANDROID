package com.example.boo_android.presentation.main.aichat

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.AppNavigationItem
import com.example.boo_android.BooTextField
import com.example.boo_android.R
import com.example.boo_android.MyMessageBubble
import com.example.boo_android.ReplyMessageBubble
import androidx.compose.material3.Icon
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.Send
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.request.SendChatRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("Range")
@Composable
fun AiChatDetailScreen(
    aiId: String,
    aiText: String,
    navController: NavController,
) {
    var title = "Boo!"
    var description = ""
    var icon = R.drawable.ic_ai_1
    var showSheet by remember { mutableStateOf(false) }
    Log.d("TEST5", aiId.toString())
    when(aiText) {
        "꼬마 유령 Boo!" -> {
            title = "Boo!"
            description = """
                지금, 말하고 싶은 게 있어서 온 거지?
                무서웠던 일… 말해줄래?
                내가 잘 들어줄게.
                    """.trimIndent()
            icon = R.drawable.ic_ai_1
        }
        "강무진" -> {
            title = "강무진"
            description = """
                    야, 누가 너한테 무슨 짓을 한 거야?
                혼자 겪었으면 진짜 빡셌을텐데, 무슨 일이야?
            괜찮아, 다 들어줄 테니까 걱정하지 마. 내가 옆에 있어.
                    """.trimIndent()
            icon = R.drawable.ic_ai_2
        }
        "책벌레 엘리" -> {
            title = "엘리"
            description = """
                    무언가… 설명되지 않는 일이 있었던 건가요?
                    지금도 기억이 또렷하다면, 분명 의미가 있을 거예요.
                    천천히 이야기해주세요. 하나도 놓치지 않을게요.
                    """.trimIndent()
            icon = R.drawable.ic_ai_3
        }
        "낭랑 18세 쏘쏘" -> {
            title = "쏘쏘"
            description = """
                    꺄! 뭔 일 있었지?! 나 지금 너무 기대돼!
                    무서운 거라면 나 쏘쏘가 빠질 수 없지 후후
                    내가 완전 몰입해서 들어줄게!!
                    """.trimIndent()
            icon = R.drawable.ic_ai_4
        }
    }

    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() } // Pair<message, isUserMessage>
    var inputText by remember { mutableStateOf("") }
    var serverText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF192432), // 위쪽 컬러
                        Color(0xFF060D23)  // 아래쪽 컬러
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "$title 와의 채팅",
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable {
                        showSheet = true
                    },
            ) {
                Text(
                    modifier = Modifier.background(color = Color(0xFF060D23))
                        .padding(
                            vertical = 8.dp,
                            horizontal = 20.dp,
                        ),
                    text = "대화 종료하기",
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
        if (inputText.isNotBlank()) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 60.dp),
                painter = painterResource(icon),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = description,
                textAlign = TextAlign.Center,
                color = Color(0xFF888D96)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            items(messages) { (message, isUserMessage) ->
                if (isUserMessage) {
                    MyMessageBubble(message = message)
                } else {
                    ReplyMessageBubble(message = message)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BooTextField(
                value = inputText,
                onValueChange = { inputText = it },

                    onSendClick = {
                    if (inputText.isNotBlank()) {
                        val currentInputText = inputText // 현재 inputText 값을 로컬 변수에 저장
                        messages.add(currentInputText to true) // Add user message
                        inputText = "" // 입력 필드는 즉시 초기화
                        Log.d("TEST10", aiId)
                        CoroutineScope(Dispatchers.IO).launch {
                            kotlin.runCatching {
                                ApiProvider.chatApi.sendChat(
                                    SendChatRequest(
                                        text = currentInputText, // 저장된 로컬 변수 사용
                                        reportId = aiId
                                    )
                                )
                            }.onSuccess {
                                Log.d("TEST", it.toString())
                                messages.add(it.content to false)
                            }.onFailure {
                                Log.d("TEST", it.toString())
                            }

                    }
                }
                },
                placeholderText = "메시지를 입력하세요",
                modifier = Modifier.weight(1f),
            )
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                containerColor = Color(0xFF192432)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "대화를 종료하시겠습니까?",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "${title}와의 대화가 종료되면, 나는 대화를 바탕으로 AI가 괴담 리포트를 작성해줍니다.\n" +
                                "괴담 리포트에서는 AI가 남긴 괴담 코멘트와 공포 등급을 확인할 수 있습니다.\n" +
                                "생성된 리포트는 ‘챗봇 기록’ 탭에서도 확인 가능합니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(60.dp))
                    Button(
                        onClick = {
                            navController.navigate(AppNavigationItem.AiChatFinish.route + "/$aiId")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0070FF)
                        )
                    ) {
                        Text(text = "대화 종료하기")
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}