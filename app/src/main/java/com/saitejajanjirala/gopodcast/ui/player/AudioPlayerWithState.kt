package com.saitejajanjirala.gopodcast.ui.player
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import com.saitejajanjirala.gopodcast.R
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(
    navController: NavHostController,
    viewModel: MainViewModel ,
) {
    // State Observations
    val currentPosition by viewModel.currentPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val podCastResult by viewModel.miniPlayer.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            viewModel.setExpanded(false)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background Image
        ThumbnailBackground(podCastResult?.thumbnail)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            // Podcast Thumbnail
            AsyncImage(
                model = podCastResult?.thumbnail,
                contentDescription = "Podcast Thumbnail",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            // Podcast Title
            Text(
                text = podCastResult?.titleOriginal ?: "Podcast Title",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Progress Bar with Slider
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Slider(
                    value = if (duration > 0) currentPosition / duration.toFloat() else 0f,
                    onValueChange = {
                        viewModel.seekTo((it * duration).toLong())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color.White,
                        inactiveTrackColor = Color.Gray
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentPosition),
                        color = Color.White
                    )
                    Text(
                        text = formatTime(duration),
                        color = Color.White
                    )
                }
            }

            // Playback Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.seekBy(-10_000) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_backward),
                        contentDescription = "Rewind 10s",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    if (isPlaying) viewModel.pause() else viewModel.play()
                }) {
                    Icon(
                        painter = if (isPlaying) painterResource(R.drawable.ic_pause) else painterResource(R.drawable.ic_play),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
                IconButton(onClick = { viewModel.seekBy(10_000) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_forward),
                        contentDescription = "Forward 10s",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun ThumbnailBackground(thumbnailUrl: String?) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur(100.dp), // Blur effect
        contentScale = ContentScale.Crop
    )
}
@Composable
fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    val hours = (milliseconds / (1000 * 60 * 60))
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
