package com.example.recipesapp.repository

import com.example.recipesapp.data.RecipesResponse
import com.example.recipesapp.network.ApiInterface
import retrofit2.Response


class RecipesRepositoryImpl(private val apiInterface: ApiInterface): RecipesRepository {

    override suspend fun getRandomRecipes(): Response<RecipesResponse> = apiInterface.getRandomRecipes()

    override suspend fun getSearchRecipes(tags: String): Response<RecipesResponse> =apiInterface.getSearchRecipes(tags)

}