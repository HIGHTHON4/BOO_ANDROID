package com.example.boo_android.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.BooTextField
import com.example.boo_android.R
import com.example.boo_android.ui.theme.Pretendard

@SuppressLint("Range")
@Composable
fun AiChatScreen(
    navController: NavController,
) {
    var text by remember { mutableStateOf("") }
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
                text = "Boo! 와의 채팅",
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp)),
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
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
            painter = painterResource(R.drawable.ic_app_mskt),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = """
                지금, 말하고 싶은 게 있어서 온 거지?
                무서웠던 일… 말해줄래?
                내가 잘 들어줄게.
                    """.trimIndent(),
            textAlign = TextAlign.Center,
            color = Color(0xFF888D96)
        )
        Spacer(modifier = Modifier.weight(1f))
        BooTextField(
            modifier = Modifier.padding(bottom = 10.dp),
            value = text,
            onValueChange = { text = it },
            placeholderText = "입력해주세요",
        )
    }
}

@Composable
fun MyMessageBubble(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFF396BEE), // 파란색
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

// 2. 답장(상대방) 메시지 (왼쪽, 회색톤)
@Composable
fun ReplyMessageBubble(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFF232B38), // 진회색
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                color = Color(0xFFE3E6EA), // 연한 회색
                fontSize = 16.sp
            )
        }
    }
}