package com.saitejajanjirala.gopodcast.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult

@Composable
fun PodCastsList(podcasts: List<PodCastResult>,onPodcastClick: (PodCastResult) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(podcasts) { podcast ->
            PodcastItem(podcast)
        }
    }
    
}