package com.example.recipesapp.repository

import com.example.recipesapp.network.ApiInterface
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val recipesApi: ApiInterface) {

    suspend fun getRandomRecipes(tags: String) = recipesApi.getRandomRecipes(tags)
}