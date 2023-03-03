package com.example.recipesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipesapp.data.DbRecipes

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: DbRecipes)

    @Delete
    suspend fun delete(recipe: DbRecipes)

    @Query("SELECT * FROM DbRecipes")
    fun getAllRecipes(): LiveData<List<DbRecipes>>

    @Query("SELECT * FROM DbRecipes WHERE title LIKE :title LIMIT 1")
    suspend fun getByTitle(title: String): DbRecipes

}