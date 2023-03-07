package com.example.recipesapp.repository

import com.example.recipesapp.data.RecipesResponse
import retrofit2.Response

interface RecipesRepository {

    suspend fun getRandomRecipes(): Response<RecipesResponse>

    suspend fun getSearchRecipes(tags: String): Response<RecipesResponse>
}