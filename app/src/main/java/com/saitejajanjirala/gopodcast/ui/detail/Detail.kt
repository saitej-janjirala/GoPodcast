package com.saitejajanjirala.gopodcast.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.saitejajanjirala.gopodcast.ui.components.HtmlText
import com.saitejajanjirala.gopodcast.ui.util.Screen

@Composable
fun DetailScreen(navController: NavHostController,podCastResult: PodCastResult,onPlayClicked : (PodCastResult) -> Unit = {}){
    Column (Modifier.padding(8.dp)) {
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .background(
                        color = Color(0xFFF3F0F0)
                    )
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    Modifier.size(24.dp)
                )
            }

            Text(
                text = podCastResult.titleOriginal ?: "",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),

                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
            )
        }
        Column(Modifier.verticalScroll(rememberScrollState())){
            Spacer(Modifier.height(16.dp))
            AsyncImage(
                model = podCastResult.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(12.dp))
            CustomButton("Play",Modifier.align(Alignment.CenterHorizontally)) {
                onPlayClicked(podCastResult)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Description",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(Modifier.height(8.dp))
            HtmlText(
                htmlContent = podCastResult.descriptionOriginal ?: "",
                Modifier.padding(start = 12.dp)
            )
            Spacer(Modifier.height(12.dp))
        }
    }

}

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .background(Color.Transparent),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        PlayIcon()
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
        )
    }
}

@Composable
fun PlayIcon() {
    IconButton(
        onClick = {
            /* Handle click */
        },
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play",
            modifier = Modifier.size(20.dp), // Icon size
            tint = Color.Blue // Blue play arrow color
        )
    }
}
