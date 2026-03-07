package com.example.starwarsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.model.Character
import com.example.starwarsapp.repository.CharacterRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

class CharacterViewModel : ViewModel() {

    private val repository = CharacterRepository()

    var characters by mutableStateOf<List<Character>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {

        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            //delay(5000) --> Zum testen des Loading Prozesses

            try {

                val response = repository.getCharacters()

                if (response.isSuccessful) {

                    val result = response.body()?.data ?: emptyList()

                    if (result.isEmpty()) {
                        errorMessage = "No characters found."
                    } else {
                        characters = result
                    }

                } else {
                    errorMessage = "Failed to load characters."
                }

            } catch (e: Exception) {
                errorMessage = "Network error. Please try again."
            }

            isLoading = false
        }
    }
}