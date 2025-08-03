package com.example.boo_android.presentation.main.strangesend

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.AppNavigationItem
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.response.TodayHorrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import kotlin.time.Clock.System.now

@SuppressLint("DefaultLocale")
@Composable
fun StrangeSendScreen(
    navController: NavController,
) {
    var storyTitles by remember { mutableStateOf<List<TodayHorrorResponse>>(emptyList()) }
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
        Spacer(Modifier.height(174.dp))

        // 다음 괴담까지 남은 시간
        Text(
            "다음 괴담까지 남은 시간",
            color = Color(0xFFD0D8E7),
            fontSize = 16.sp
        )
        Spacer(Modifier.height(12.dp))

        // 타이머
        LiveTimeDisplay()

        Spacer(Modifier.height(32.dp))

        // 오늘의 랜덤 괴담은?
        Text(
            "오늘의 랜덤 괴담은?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.height(18.dp))

        Log.d("TEST", storyTitles.toString())
        storyTitles.forEachIndexed { index, title ->
            NumberedListItem(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
                number = index,
                text = title.title,
                onClick = {
                    navController.navigate(AppNavigationItem.StrangeSendDetail.route + "/${title.reportId}")
                }
            )
        }
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

@Composable
fun NumberedListItem(
    number: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A3441)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 번호
            Text(
                text = "${number}.",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 12.dp)
            )

            // 텍스트
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            // 화살표 아이콘
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "이동",
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun LiveTimeDisplay(
    targetHour: Int = 4,
    targetMinute: Int = 44,
    targetSecond: Int = 44,
    modifier: Modifier = Modifier
) {
    var remainingTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)
            val currentSecond = calendar.get(Calendar.SECOND)

            // 현재 시간을 초 단위로 변환
            val currentTotalSeconds = currentHour * 3600 + currentMinute * 60 + currentSecond

            // 목표 시간을 초 단위로 변환
            var targetTotalSeconds = targetHour * 3600 + targetMinute * 60 + targetSecond

            // 만약 목표 시간이 현재 시간보다 이르면 내일의 목표 시간으로 계산
            if (targetTotalSeconds <= currentTotalSeconds) {
                targetTotalSeconds += 24 * 3600 // 다음날 추가
            }

            // 남은 시간 계산
            val remainingSeconds = targetTotalSeconds - currentTotalSeconds

            val hours = remainingSeconds / 3600
            val minutes = (remainingSeconds % 3600) / 60
            val seconds = remainingSeconds % 60

            remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            delay(1000) // 1초마다 업데이트
        }
    }

    TimeDisplay(
        time = remainingTime,
        modifier = modifier
    )
}
@Composable
fun TimeDisplay(
    time: String = "04:44:25",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            color = Color.White,
            fontSize = 60.sp,
            fontWeight = FontWeight.Thin,
            letterSpacing = 2.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
