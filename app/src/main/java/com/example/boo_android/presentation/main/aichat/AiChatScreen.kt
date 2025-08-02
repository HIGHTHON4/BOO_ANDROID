package com.example.boo_android.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.boo_android.AiComponent
import com.example.boo_android.AppNavigationItem
import com.example.boo_android.R
import com.example.boo_android.ui.theme.Pretendard

@Composable
fun AiChatScreen(
    navController: NavController
) {
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
        AiComponent(
            icon = R.drawable.ic_ai_1,
            title = "꼬마 유령 Boo!",
            content = "말수가 적고 따뜻한 공감러 \n" +
                    "감정의 결을 놓치지 않고, 한 얘기에 길게 집중해줌\n" +
                    "조용히, 천천히 얘기할 때 추천",
            onClick = {
                navController.navigate(AppNavigationItem.AiChatDetail.route + "/꼬마 유령 Boo!")
            }
        )
        AiComponent(
            icon = R.drawable.ic_ai_2,
            title = "강무진",
            content = "언제나 내 편이 되어주는 든든한 AI \n" +
                    "겁 먹었다고 하면 언제 누가 그랬냐고 물어봐주면서 한 대 때려줄까? 하고 물어봄 \n" +
                    "헛웃음 치다 어느 순간 진지해진다",
            onClick = {
                navController.navigate(AppNavigationItem.AiChatDetail.route + "/강무진")
            }
        )
        AiComponent(
            icon = R.drawable.ic_ai_3,
            title = "책벌레 엘리",
            content = "차분하고 분석적인 뼛속까지 대문자 T 사서\n" +
                    "괴담을 들으면 관련 사례를 찾아 해석하려 들지만\n" +
                    "진짜 비과학적인 얘기엔 누구보다 겁을 먹는다.",
            onClick = {
                navController.navigate(AppNavigationItem.AiChatDetail.route + "/책벌레 엘리")
            }
        )
        AiComponent(
            icon = R.drawable.ic_ai_4,
            title = "낭랑 18세 쏘쏘",
            content = "수다쟁이 여고생 친구\n" +
                    "겁이 많지만 귀신 얘기는 너무 좋아함 \n" +
                    "혼자 상상하다 혼자 비명 지르지만 친근하다",
            onClick = {
                navController.navigate(AppNavigationItem.AiChatDetail.route + "/낭랑 18세 쏘쏘")
            }
        )
    }
}