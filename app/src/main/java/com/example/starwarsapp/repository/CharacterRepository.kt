package com.example.starwarsapp.repository

import com.example.starwarsapp.model.CharacterResponse
import com.example.starwarsapp.remote.ApiService
import retrofit2.Response

class CharacterRepository {

    suspend fun getCharacters(): Response<CharacterResponse> {
        return ApiService.getInstance().getCharacters()
    }

}