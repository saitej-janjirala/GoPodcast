package com.saitejajanjirala.gopodcast.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult

@Composable
fun PodcastItem(
    podcast: PodCastResult,
    onPodcastClick: (PodCastResult) -> Unit,
    onPlayClick: (PodCastResult) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp).clickable {
                onPodcastClick(podcast)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = podcast.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = podcast.titleOriginal?:"No Title",
                    style = MaterialTheme.typography.title3
                )
                Spacer(Modifier.height(8.dp))
                HtmlText(
                    htmlContent = podcast.descriptionOriginal ?: "No description",
                    max = 3
                )
                PlayButton(podcast) {
                    onPlayClick(podcast)
                }
            }
        }
    }
}
