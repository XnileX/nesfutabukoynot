package com.fruitandveggie


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fruitandveggie.home.HomeScreen
import com.fruitandveggie.objectdetector.ObjectDetectorHelper
import com.fruitandveggie.options.OptionsScreen
import com.fruitandveggie.ui.theme.FruitAndVeggieTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.res.painterResource
import com.fruitandveggie.games.GuessingGameScreen
import androidx.compose.material3.Text
import com.fruitandveggie.games.VideoScreen
import androidx.compose.material.icons.filled.PlayArrow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObjectDetectionExampleApp()
//            FruitAndVeggieTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ObjectDetectionExampleApp(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

@Composable
fun ObjectDetectionExampleApp() {
    var threshold by rememberSaveable { mutableStateOf(0.4f) }
    var maxResults by rememberSaveable { mutableStateOf(5) }
    var delegate by rememberSaveable { mutableStateOf(ObjectDetectorHelper.DELEGATE_CPU) }
    var mlModel by rememberSaveable { mutableStateOf(ObjectDetectorHelper.MODEL_EFFICIENTDETV0) }

    // Bottom navigation state
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val tabs = listOf("Home", "Games", "Video")

    // Add navigation state for OptionsScreen
    var showOptions by rememberSaveable { mutableStateOf(false) }

    FruitAndVeggieTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showOptions) {
                OptionsScreen(
                    threshold = threshold,
                    setThreshold = { threshold = it },
                    maxResults = maxResults,
                    setMaxResults = { maxResults = it },
                    delegate = delegate,
                    setDelegate = { delegate = it },
                    mlModel = mlModel,
                    setMlModel = { mlModel = it },
                    onBackButtonClick = { showOptions = false }
                )
            } else {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("Home") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                icon = { Icon(Icons.Default.Star, contentDescription = "Games") },
                                label = { Text("Games") }
                            )
                            NavigationBarItem(
                                selected = selectedTab == 2,
                                onClick = { selectedTab = 2 },
                                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Video") },
                                label = { Text("Video") }
                            )
                        }
                    }
                ) { paddingValues ->
                    when (selectedTab) {
                        0 -> HomeScreen(
                            onOptionsButtonClick = { showOptions = true },
                            threshold = threshold,
                            maxResults = maxResults,
                            delegate = delegate,
                            mlModel = mlModel,
                        )
                        1 -> GuessingGameScreen()
                        2 -> VideoScreen()
                    }
                }
            }
        }
    }
}