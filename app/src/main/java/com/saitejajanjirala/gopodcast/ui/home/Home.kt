package com.saitejajanjirala.gopodcast.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import com.saitejajanjirala.gopodcast.domain.remote.Genre
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.home.HomeViewModel
import com.saitejajanjirala.gopodcast.domain.util.Result
import com.saitejajanjirala.gopodcast.ui.components.PodCastsList
import com.saitejajanjirala.gopodcast.ui.util.Screen

@Composable
fun HomeScreen(navController: NavHostController,
               viewModel: HomeViewModel = hiltViewModel(),
               onPlayClicked : (PodCastResult) -> Unit = {}) {

    val genresState by viewModel.genres.collectAsState()
    val podcastsState by viewModel.podCasts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = {},
            label = { Text("Search Podcasts") },
            trailingIcon = {
                Icon(Icons.Default.Search,"search podcasts")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .pointerInput(true) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null ) {
                            navController.navigate(Screen.SEARCH)
                        }
                    }
                }
        )

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            when (genresState) {
                is Result.Success -> {
                    val genres = (genresState as Result.Success<List<Genre>>).data
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(genres.size) { index ->
                            val genre = genres[index]
                            Text(
                                text = genre.name ?: "",
                                color = if (genre.isSelected) Color.White else Color.Black,
                                modifier = Modifier
                                    .background(
                                        if (genre.isSelected) Color.Blue else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        viewModel.onGenreSelected(index)
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

                is Result.Loading -> {
                    if ((genresState as Result.Loading<List<Genre>>).isLoading) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Error -> Text("Error loading genres")
            }
        }

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            when (podcastsState) {
                is Result.Success -> {
                    val podcasts = (podcastsState as Result.Success<List<PodCastResult>>).data
                    PodCastsList(
                        podcasts,
                        onPodcastClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("podcastResult",it)
                            navController.navigate(Screen.DETAIL)
                        },
                        onPlayClick = {
                            onPlayClicked(it)
                        }
                    )
                }
                is Result.Loading -> {
                    if ((podcastsState as Result.Loading<List<PodCastResult>>).isLoading) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Error -> Text("Error loading podcasts")
            }
        }
    }
}



