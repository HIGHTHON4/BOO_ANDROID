import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.boo_android.R
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.data.response.MyReportDetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HistoryDetailScreen(
    reportId: String,
    navController: NavController,
) {
    var detailData: MyReportDetailResponse? by remember {
        mutableStateOf<MyReportDetailResponse?>(
            null
        )
    }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                ApiProvider.authApi.fetchMyReportDetail(
                    reportId = reportId
                )
            }.onSuccess {
                detailData = it
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    // detailData가 null이 아닐 때만 UI 렌더링
    detailData?.let { data ->
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
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_left_back),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Text(
                    text = "${data.aiName}의 한 마디", // data 사용
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            // Header


            // Main Content Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF262E3F)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Title
                    Text(
                        text = data.title, // data 사용
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Grade
                    Text(
                        text = "${data.fearLevel}등급", // data 사용
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Story Title
                    Text(
                        text = "쏘쏘의 한 마디",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Story Content
                    Text(
                        text = data.summary, // data 사용
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Emoji
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "😖",
                            fontSize = 32.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 새 채팅 시작하기 Button
                        Button(
                            onClick = { /* Handle click */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF007AFF)
                            ),
                            shape = RoundedCornerShape(26.dp)
                        ) {
                            Text(
                                text = "새 채팅 시작하기",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            // Bottom Buttons
        }
    }
}