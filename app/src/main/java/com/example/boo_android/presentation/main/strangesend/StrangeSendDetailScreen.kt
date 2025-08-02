package com.example.boo_android.presentation.main.strangesend

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.R
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.response.TodayHorrorDetailResponse
import com.example.boo_android.data.response.TodayHorrorResponse
import com.google.android.gms.common.api.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StrangeSendDetailScreen(
    reportId: String,
    navController: NavController,
) {
    var ssdetail by remember { mutableStateOf<TodayHorrorDetailResponse>(
        TodayHorrorDetailResponse(
            text = "",
            content = "",
            title = ""
        )
    ) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                ApiProvider.horrorApi.fetchTodayHorrorDetail(
                    reportId = reportId
                )
            }.onSuccess {
                ssdetail = it

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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left_back),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = ssdetail.title,
                color = Color.White,
                fontSize = 18.sp,
                lineHeight = 26.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = ssdetail.title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 인용문 (따옴표가 있는 텍스트)
        Text(
            text = ssdetail.text,
            color = Color.White,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        StoryCard(
            modifier = Modifier.background(Color.Transparent),
            title = ssdetail.content
        )
    }
}

@Composable
fun StoryCard(
    title: String,
    iconResId: Int? = null,
    emoji: String? = null,
    onClick: () -> Unit = {},
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
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아이콘 또는 이모지
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        Color.White,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                when {
                    emoji != null -> {
                        Text(
                            text = emoji,
                            fontSize = 32.sp
                        )
                    }
                    iconResId != null -> {
                        Icon(
                            painter = painterResource(iconResId),
                            contentDescription = title,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                    }
                    else -> {
                        // 기본 아이콘
                        Text(
                            text = "👻",
                            fontSize = 32.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 텍스트 정보
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Color(0xFFB0B8C1),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}