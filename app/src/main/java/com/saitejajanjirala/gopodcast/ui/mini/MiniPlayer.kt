package com.saitejajanjirala.gopodcast.ui.mini

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import com.saitejajanjirala.gopodcast.R
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.main.MainViewModel

@Composable
fun MiniPlayer(
    viewModel: MainViewModel,
    onExpand: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val podCastResult by viewModel.miniPlayer.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.play()
            } else if (event == Lifecycle.Event.ON_PAUSE || event == Lifecycle.Event.ON_STOP) {
                viewModel.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {

            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    BottomAppBar(
        containerColor = Color.Blue,
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        AsyncImage(
            model = podCastResult?.thumbnail,
            contentDescription = "Thumbnail",
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = podCastResult?.titleOriginal ?: "Podcast",
            modifier = Modifier.weight(1f),
            color = Color.White,
            maxLines = 1
        )
        IconButton(onClick = { if (isPlaying) viewModel.pause() else viewModel.play() }) {
            Icon(
                painter = if (isPlaying) painterResource(R.drawable.ic_pause) else painterResource(R.drawable.ic_play),
                contentDescription = null
            )
        }
        IconButton(onClick = {
            onExpand()
        }) {
            Icon(
                painter = painterResource(R.drawable.ic_expand),
                contentDescription = "Expand",
                tint = Color.White
            )
        }
    }
}
