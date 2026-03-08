package com.example.starwarsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Info",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This app was produced for the Mobile Programming with Native Technologies course at OAMK.\n\n" +
                    "It displays characters from the Star Wars universe by retrieving data from an online API.\n\n" +
                    "It allows users to browse a list of characters, view their images, and read short descriptions.\n\n" +
                    "The application demonstrates how Android apps can fetch and present external data while exploring the iconic world of Star Wars.\n\n" +
                    "Thanks for the informative course the last few weeks!"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}