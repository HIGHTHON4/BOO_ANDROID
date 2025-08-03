package com.example.boo_android

import HistoryDetailScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.boo_android.presentation.main.aichat.AiChatDetailScreen
import com.example.boo_android.presentation.main.aichat.AiChatFinishScreen
import com.example.boo_android.presentation.main.aichat.AiChatScreen
import com.example.boo_android.ui.theme.BOO_ANDROIDTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.boo_android.data.api.ApiProvider
import com.example.boo_android.presentation.main.strangesend.StrangeSendScreen
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.tasks.OnCompleteListener
import android.util.Log
import android.widget.Toast
import com.example.boo_android.presentation.main.history.HistoryScreen
import com.example.boo_android.presentation.main.strangesend.StrangeSendDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ApiProvider.initialize(this)
        ApiProvider.saveToken("eyJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2VyIiwiZXhwIjoxNzU1MTU1Mjc5LCJpYXQiOjE3NTQxNTUyODB9.MrxjrBSHHqIrmjus3PPrdadGpCev8H3pUAsQO_1mylI")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            BOO_ANDROIDTheme {
                BaseApp()
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("TEST", token)

            // Log and save token to SharedPreferences
            Log.d(TAG, "FCM Registration Token: $token")
            Toast.makeText(baseContext, token, Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomMenu.AI_CHAT,
        BottomMenu.STRANGE_SEND,
        BottomMenu.HISTORY,
    )
    val noRippleSource = remember { MutableInteractionSource() }

    BottomAppBar (
        containerColor = Color(0xFF060D23),
        contentColor = Color(0xFF3F414E),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(item.icon), contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 9.sp) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF5F6074),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF3F414E),
                    unselectedTextColor = Color(0xFF3F414E),
                    indicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@Composable
private fun BaseApp() {
    val navController = rememberNavController()
    var showBottomNav = remember { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = AppNavigationItem.Main.route
    ) {
        composable(AppNavigationItem.SignIn.route) {

        }
        composable(AppNavigationItem.SignUp.route) {

        }
        composable(AppNavigationItem.Main.route) {
            val mainAppNavController = rememberNavController()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (showBottomNav.value) {
                        BottomAppBar { }
                        BottomNavigationBar(
                            navController = mainAppNavController
                        )
                    }
                },
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = mainAppNavController,
                    startDestination = AppNavigationItem.AiChat.route
                ) {
                    composable(AppNavigationItem.AiChat.route) {
                        AiChatScreen(navController = mainAppNavController) // NavController 제거
                    }
                    composable(AppNavigationItem.AiChatDetail.route + "/{aiId}/{aiText}",
                        arguments = listOf(
                            navArgument("aiId") { type = NavType.StringType },
                            navArgument("aiText") { type = NavType.StringType }
                        )) {
                        showBottomNav.value = false
                        AiChatDetailScreen(
                            aiId = it.arguments?.getString("aiId") ?: "",
                            aiText = it.arguments?.getString("aiText") ?: "",
                            navController = mainAppNavController,
                        )
                    }
                    composable(AppNavigationItem.AiChatFinish.route + "/{aiId}",
                        arguments = listOf(
                            navArgument("aiId") { type = NavType.StringType }
                        )) {
                        showBottomNav.value = true
                        AiChatFinishScreen(
                            aiId = it.arguments?.getString("aiId") ?: "",
                            navController = mainAppNavController
                        )
                    }
                    composable(AppNavigationItem.StrangeSend.route) {
                        StrangeSendScreen(navController = mainAppNavController)
                    }
                    composable(AppNavigationItem.History.route) {
                        HistoryScreen(navController = mainAppNavController)
                    }
                    composable(AppNavigationItem.HistoryDetail.route + "/{reportId}",
                        arguments = listOf(
                            navArgument("reportId") { type = NavType.StringType }
                        )) {
                        HistoryDetailScreen(
                            reportId = it.arguments?.getString("reportId") ?: "",
                            navController = mainAppNavController
                        )
                    }
                    composable(AppNavigationItem.StrangeSendDetail.route + "/{reportId}",
                        arguments = listOf(
                            navArgument("reportId") { type = NavType.StringType}
                        )) {
                        StrangeSendDetailScreen(
                            reportId = it.arguments?.getString("reportId") ?: "",
                            navController = mainAppNavController
                        )
                    }
                }
            }
        }
    }
}