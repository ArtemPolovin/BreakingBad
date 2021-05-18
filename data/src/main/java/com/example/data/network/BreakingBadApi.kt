package com.example.data.network

import com.example.data.common.BREAKING_BAD_BASE_URL
import com.example.data.modelsapi.characters.BreakingBadCharacterApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BreakingBadApi {

    @GET("/api/characters")
    suspend fun getBreakingBadCharacters(): Response<BreakingBadCharacterApi>

    companion object {
        operator fun invoke(): BreakingBadApi {
            return Retrofit.Builder()
                .baseUrl(BREAKING_BAD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BreakingBadApi::class.java)
        }
    }
}