package com.example.recipesapp.repository

import androidx.lifecycle.LiveData
import com.example.recipesapp.data.DbRecipes

interface LocalRecipesRepository {

    suspend fun insertRecipe(recipe: DbRecipes)

    fun getFavouriteRecipes() : LiveData<List<DbRecipes>>

    suspend fun deleteRecipe(recipe: DbRecipes)

    suspend fun getByTitle(recipe: DbRecipes): DbRecipes

}