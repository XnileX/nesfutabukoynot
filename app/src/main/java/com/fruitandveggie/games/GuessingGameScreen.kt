package com.fruitandveggie.games

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun GuessingGameScreen() {
    val fruits = listOf(
        Fruit("Apple", "apple"),
        Fruit("Banana", "banana"),
        Fruit("Grape", "grape"),
        Fruit("Orange", "orange"),
        Fruit("Mango", "mango"),
        Fruit("Papaya", "papaya"),
        Fruit("Pineapple", "pineapple"),
        Fruit("Radish", "radish"),
        Fruit("Strawberry", "strawberry"),
        Fruit("Tomato", "tomato"),
        Fruit("Guava", "guava"),
        Fruit("Cabbage", "cabbage"),
        Fruit("Eggplant", "eggplant"),
        Fruit("Garlic", "garlic"),
        Fruit("Ginger", "ginger")
    )
    var currentFruit by remember { mutableStateOf(fruits.random()) }
    var guess by remember { mutableStateOf(TextFieldValue("")) }
    var feedback by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Guess the Fruits & Vegetables!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(
                id = getDrawableId(currentFruit.drawableName)
            ),
            contentDescription = "Fruit Image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = guess,
            onValueChange = { guess = it },
            label = { Text("Your Guess") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (guess.text.trim().equals(currentFruit.name, ignoreCase = true)) {
                feedback = "Correct! It's ${currentFruit.name}."
                val player = MediaPlayer.create(context, com.fruitandveggie.R.raw.correct)
                player.setOnCompletionListener {
                    it.release()
                    currentFruit = fruits.random()
                    guess = TextFieldValue("")
                    feedback = ""
                }
                player.start()
            } else {
                feedback = "Try again!"
                val player = MediaPlayer.create(context, com.fruitandveggie.R.raw.incorrect)
                player.setOnCompletionListener { it.release() }
                player.start()
            }
        }) {
            Text("Guess")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(feedback)
    }
}

data class Fruit(val name: String, val drawableName: String)

@Composable
fun getDrawableId(name: String): Int {
    val context = LocalContext.current
    return remember(name) {
        context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}
