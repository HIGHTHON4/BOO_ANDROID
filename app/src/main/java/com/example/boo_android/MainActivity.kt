package com.example.boo_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.boo_android.presentation.main.AiChatScreen
import com.example.boo_android.ui.theme.BOO_ANDROIDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BOO_ANDROIDTheme {
                BaseApp()
            }
        }

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and save token to SharedPreferences
//            Log.d(TAG, "FCM Registration Token: $token")
//        })
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

    BottomAppBar (
        containerColor = Color.White,
        contentColor = Color(0xFF3F414E),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = stringResource(id = item.title)) },
                label = { Text(text = stringResource(id = item.title), fontSize = 9.sp) },
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
                )
            )
        }
    }
}

@Composable
private fun BaseApp() {
    val navController = rememberNavController()
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
                    BottomAppBar { }
                    BottomNavigationBar(navController = mainAppNavController)
                },
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = mainAppNavController,
                    startDestination = BottomMenu.AI_CHAT.route
                ) {
                    composable(BottomMenu.AI_CHAT.route) {
                        AiChatScreen(
                            navController = mainAppNavController,
                        )
                    }
                    composable(BottomMenu.STRANGE_SEND.route) {

                    }
                    composable(BottomMenu.HISTORY.route) {

                    }
                }
            }
        }
    }
}