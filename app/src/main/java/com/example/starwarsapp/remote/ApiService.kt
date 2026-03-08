package com.example.starwarsapp.remote

import com.example.starwarsapp.model.CharacterResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

const val BASE_URL = "https://starwars-databank-server.vercel.app/api/v1/"

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 200
    ): Response<CharacterResponse>

    companion object {

        var apiService: ApiService? = null

        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}