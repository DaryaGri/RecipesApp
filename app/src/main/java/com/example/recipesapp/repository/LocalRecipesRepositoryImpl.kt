package com.example.recipesapp.repository

import com.example.recipesapp.data.DbRecipes
import com.example.recipesapp.db.RecipesDao

class LocalRecipesRepositoryImpl(var dbDao: RecipesDao): LocalRecipesRepository {

    override suspend fun insertRecipe(recipe: DbRecipes) = dbDao.insertRecipe(recipe)

    override fun getFavouriteRecipes() = dbDao.getAllRecipes()

    override suspend fun deleteRecipe(recipe: DbRecipes) = dbDao.delete(recipe)

    override suspend fun getByTitle(recipe: DbRecipes): DbRecipes {
        return dbDao.getByTitle(recipe.title!!)
    }

}