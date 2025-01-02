package com.saitejajanjirala.gopodcast.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.components.PodCastsList
import com.saitejajanjirala.gopodcast.domain.util.Result
import com.saitejajanjirala.gopodcast.ui.util.Screen

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
){
    val focusManager = LocalFocusManager.current
    val podCasts by searchViewModel.podCasts.collectAsState()
    Column {
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText =it
            },
            label = { Text("Search Podcasts") },
            trailingIcon = {
                Icon(Icons.Default.Search,"search podcasts",Modifier.clickable {
                    focusManager.clearFocus()
                    if(searchText.isNotEmpty()) {
                        searchViewModel.getPodCasts(searchText)
                    }
                })
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    if(searchText.isNotEmpty()) {
                        searchViewModel.getPodCasts(searchText)
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            when(podCasts) {
                is Result.Success -> {
                    val podcasts = (podCasts as Result.Success<List<PodCastResult>>).data
                    PodCastsList(
                        podcasts,
                        onPodcastClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("podcastResult",it)
                            navController.navigate(Screen.DETAIL)
                        },
                        onPlayClick = {

                        }
                    )
                }
                is Result.Loading -> {
                    if ((podCasts as Result.Loading<List<PodCastResult>>).isLoading) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Error -> Text("Error loading podcasts")
            }
        }
    }
}