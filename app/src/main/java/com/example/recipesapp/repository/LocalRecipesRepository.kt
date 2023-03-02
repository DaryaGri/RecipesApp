package com.example.recipesapp.repository

import com.example.recipesapp.data.DbRecipes
import com.example.recipesapp.db.RecipesDao
import javax.inject.Inject

class LocalRecipesRepository @Inject constructor(var dbDao: RecipesDao) {

    suspend fun insertRecipe(recipe: DbRecipes) = dbDao.insertRecipe(recipe)

    fun getFavouriteRecipes() = dbDao.getAllRecipes()

    suspend fun deleteRecipe(recipe: DbRecipes) = dbDao.delete(recipe)
}