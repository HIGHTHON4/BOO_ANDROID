package com.example.boo_android.presentation.main.strangesend

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.response.TodayHorrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("DefaultLocale")
@Composable
fun StrangeSendScreen(
    navController: NavController,
) {
    var storyTitles = TodayHorrorResponse(
        title = "",
        reportId = UUID.randomUUID()
    )
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                ApiProvider.horrorApi.fetchTodayHorror()
            }.onSuccess {
                storyTitles = it
            }.onFailure {

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(74.dp))

        // 다음 괴담까지 남은 시간
        Text(
            "다음 괴담까지 남은 시간",
            color = Color(0xFFD0D8E7),
            fontSize = 16.sp
        )
        Spacer(Modifier.height(12.dp))

        // 타이머
        Text(
            text = String.format("%02d:%02d:%02d", 3, 3, 3),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp
        )
        Spacer(Modifier.height(32.dp))

        // 오늘의 랜덤 괴담은?
        Text(
            "오늘의 랜덤 괴담은?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(18.dp))

        // 선택지 리스트
//        storyTitles.forEachIndexed { index, title ->
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 5.dp)
//                    .background(Color(0xFF232B3A), RoundedCornerShape(10.dp))
//                    .clickable {  }
//                    .padding(vertical = 16.dp, horizontal = 18.dp)
//            ) {
//                Text(
//                    "${index + 1}. $title",
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
//            }
//        }

        Spacer(Modifier.weight(1f))

        // 하단 안내문구
        Text(
            "괴담은 매일 4시 44분 44초에 3개씩 공개됩니다.",
            color = Color(0xFFB3B3B3),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}