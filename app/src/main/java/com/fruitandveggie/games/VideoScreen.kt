package com.fruitandveggie.games

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.fruitandveggie.R
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background

@Composable
fun VideoScreen() {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var videoView: VideoView? by remember { mutableStateOf(null) }
    var selectedVideo by remember { mutableStateOf(1) }

    val videoResId = when (selectedVideo) {
        1 -> R.raw.video1
        2 -> R.raw.video2
        3 -> R.raw.video3
        else -> R.raw.video1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB5C18E))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                selectedVideo = 1
                isPlaying = false
                videoView?.apply {
                    setVideoPath("android.resource://" + context.packageName + "/" + R.raw.video1)
                    start()
                    isPlaying = true
                }
            }) { Text("Video 1") }
            Button(onClick = {
                selectedVideo = 2
                isPlaying = false
                videoView?.apply {
                    setVideoPath("android.resource://" + context.packageName + "/" + R.raw.video2)
                    start()
                    isPlaying = true
                }
            }) { Text("Video 2") }
            Button(onClick = {
                selectedVideo = 3
                isPlaying = false
                videoView?.apply {
                    setVideoPath("android.resource://" + context.packageName + "/" + R.raw.video3)
                    start()
                    isPlaying = true
                }
            }) { Text("Video 3") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AndroidView(
            factory = {
                VideoView(context).apply {
                    setVideoPath("android.resource://" + context.packageName + "/" + videoResId)
                    setMediaController(MediaController(context).apply {
                        setAnchorView(this@apply)
                    })
                    setOnPreparedListener {
                        if (isPlaying) start() else pause()
                    }
                    videoView = this
                }
            },
            update = {
                it.setVideoPath("android.resource://" + context.packageName + "/" + videoResId)
                if (isPlaying) it.start() else it.pause()
                videoView = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            videoView?.let {
                if (it.isPlaying) {
                    it.pause()
                    isPlaying = false
                } else {
                    it.start()
                    isPlaying = true
                }
            }
        }) {
            Text(if (isPlaying) "Pause" else "Play")
        }
    }
}
