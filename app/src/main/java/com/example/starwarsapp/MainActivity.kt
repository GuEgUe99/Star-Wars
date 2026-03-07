package com.example.starwarsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.starwarsapp.ui.screens.CharacterScreen
import com.example.starwarsapp.ui.screens.StartScreen
import com.example.starwarsapp.ui.theme.StarWarsAppTheme
import com.example.starwarsapp.ui.viewmodel.CharacterViewModel

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StarWarsAppTheme {
                val navController = rememberNavController()
                val viewModel: CharacterViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "start"
                    ) {
                        composable("start") {
                            StartScreen(navController)
                        }
                        composable("characters") {
                            CharacterScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

