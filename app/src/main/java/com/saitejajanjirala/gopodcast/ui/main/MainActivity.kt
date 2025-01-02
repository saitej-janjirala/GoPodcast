package com.saitejajanjirala.gopodcast.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.detail.DetailScreen
import com.saitejajanjirala.gopodcast.ui.home.HomeScreen
import com.saitejajanjirala.gopodcast.ui.mini.MiniPlayer
import com.saitejajanjirala.gopodcast.ui.player.PlayerScreen
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
                val viewModel: MainViewModel = hiltViewModel()
                val navController = rememberNavController()
                val isExpanded by viewModel.isExpanded.collectAsState()
                val currentPodCast by viewModel.miniPlayer.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if(!isExpanded && currentPodCast != null) {
                            MiniPlayer(viewModel=viewModel, onExpand = {
                                viewModel.setExpanded(true)
                                navController.navigate(Screen.PLAYER)
                            })
                        }
                        else{
                            Spacer(Modifier.padding(8.dp))
                        }
                    }) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(navController, startDestination = Screen.HOME){
                            composable(Screen.HOME){
                                HomeScreen(navController){
                                    viewModel.setUpMiniBar(it)
                                }
                            }
                            composable(Screen.SEARCH){
                                SearchScreen(navController)
                            }
                            composable(
                                route = Screen.DETAIL,
                            ){
                                val podcast = navController.previousBackStackEntry?.savedStateHandle?.get<PodCastResult>("podcastResult")
                                podcast?.let { it ->
                                    DetailScreen(navController,it){podcast->
                                        viewModel.setUpMiniBar(podcast)
                                    }
                                }
                            }
                            composable(
                                route = Screen.PLAYER,
                               ){
                                PlayerScreen(navController,viewModel)
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