package com.saitejajanjirala.gopodcast.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saitejajanjirala.gopodcast.ui.home.HomeScreen
import com.saitejajanjirala.gopodcast.ui.search.SearchScreen
import com.saitejajanjirala.gopodcast.ui.theme.GoPodcastTheme
import com.saitejajanjirala.gopodcast.ui.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoPodcastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(navController, startDestination = Screen.HOME){
                            composable(Screen.HOME){
                                HomeScreen(navController)
                            }
                            composable(Screen.SEARCH){
                                SearchScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoPodcastTheme {
        Greeting("Android")
    }
}