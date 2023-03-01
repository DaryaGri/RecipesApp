package com.example.recipesapp.repository

import com.example.recipesapp.network.ApiInterface
import javax.inject.Inject

class RecipesRepository @Inject constructor(private val recipesApi: ApiInterface) {

    suspend fun getRandomRecipes() = recipesApi.getRandomRecipes()

    suspend fun getSearchRecipes(tags: String) = recipesApi.getSearchRecipes(tags)
}