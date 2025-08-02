package com.example.boo_android.presentation.main.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import com.example.boo_android.AppNavigationItem
import com.example.boo_android.Sort
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.request.SendChatRequest
import com.example.boo_android.data.response.AiListResponse
import com.example.boo_android.data.response.MyReportResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val sortOptions = listOf("최신순", "등급순", "날짜순")
    val aiOptions = listOf("전체", "Boo!", "강무진", "엘리", "쏘쏘")
    var selectedSort by remember { mutableStateOf("") }
    var selectedAI by remember { mutableStateOf("") }
    var aiList by remember { mutableStateOf<List<AiListResponse>>(emptyList()) }
    var aiId = ""

    var newList by remember { mutableStateOf<List<MyReportResponse>>(emptyList()) }

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

//    when(selectedSort) {
//        "Boo!" -> {
//            aiList.first { it.image == "Boo!" }.id.toString()
//        }
//        ""
//    }

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
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "괴담 리포트 목록",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 20.dp)
        )
        Log.d("TEST", selectedSort.toString())
        Spacer(modifier = Modifier.height(16.dp))

        // 정렬 버튼 그룹
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "정렬",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 16.dp)
            )

            sortOptions.forEachIndexed { idx, label ->
                Button(
                    onClick = { selectedSort = label },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSort == label) Color(0xFF4F80FF) else Color(0xFF2A3441)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(end = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(12.dp))

// AI 필터 버튼 그룹
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AI",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 16.dp)
            )

            aiOptions.forEachIndexed { idx, label ->
                Button(
                    onClick = {
                        selectedAI = label
                        // 선택된 AI에 따라 필터링된 ID 리스트 생성
                        val filteredAiIds = if (selectedAI == "전체") {
                            aiList.map { it.id.toString() }
                        } else {
                            aiList.filter { it.name == selectedAI }.map { it.id.toString() }
                        }
                        Log.d("HistoryScreen", "Filtered AI IDs: $filteredAiIds")
                        var enum = Sort.TIME
                        when(selectedSort) {
                            "최신순" -> {
                                enum = Sort.TIME
                            }
                            "등급순" -> {
                                enum = Sort.LEVEL
                            }
                            "날짜순" -> {
                                enum = Sort.LAST
                            }
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            kotlin.runCatching {
                                ApiProvider.authApi.fetchMyReport(
                                    sort = enum, // selectedSort는 현재 인덱스로 저장되므로 문자열로 변환 필요
                                    ai = filteredAiIds
                                )
                            }.onSuccess {
                                newList = it
                                Log.d("HistoryScreen", "fetchMyReport success: $it")
                            }.onFailure {
                                Log.e("HistoryScreen", "fetchMyReport failed: ${it.message}")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedAI == label) Color(0xFF4F80FF) else Color(0xFF2A3441)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(end = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        var reports: List<ReportItem> = List(newList.size) {
            var report = newList[it]
            ReportItem(
                title = report.title,
                date = report.date,
                grade = report.level,
                color = when (it % 4) {
                    0 -> Color(0xFF3D4354)
                    1 -> Color(0xFF673737)
                    2 -> Color(0xFF39633F)
                    else -> Color(0xFF6B485C)
                },
                reportId = report.reportId
            )

        }

        // 리스트
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            Log.d("TEST", newList.toString())
            items(newList.size) { index ->
                val report = reports[index]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .background(report.color, RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate(AppNavigationItem.HistoryDetail.route+ "/${report.reportId}")
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = report.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Text(text = report.date, color = Color(0x88FFFFFF), fontSize = 14.sp, modifier = Modifier.padding(end = 12.dp))
                        Text(text = report.grade, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

suspend fun server() {

}

data class ReportItem(val title: String, val date: String, val grade: String, val color: Color, val reportId: UUID)