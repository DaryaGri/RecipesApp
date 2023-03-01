package com.example.recipesapp.network

import com.example.recipesapp.data.RecipesResponse
import com.example.recipesapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number")
        number: Int = 50,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<RecipesResponse>

    @GET("recipes/random")
    suspend fun getSearchRecipes(
        @Query("tags")
        cuisines: String,
        @Query("number")
        number: Int = 50,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<RecipesResponse>

}