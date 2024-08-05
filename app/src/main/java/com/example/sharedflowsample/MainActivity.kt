package com.example.sharedflowsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sharedflowsample.ui.feature.home.HomeScreen
import com.example.sharedflowsample.ui.feature.webview.WebViewScreen
import com.example.sharedflowsample.ui.theme.SharedFlowSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedFlowSampleTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(
            route = "home",
        ) {
            HomeScreen(
                navController = navController,
            )
        }
        composable(
            route = "webView/?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType }),
        ) {
            it.arguments?.getString("url")?.let { url ->
                WebViewScreen(
                    navController = navController,
                    url = url,
                )
            }
        }
    }
}
