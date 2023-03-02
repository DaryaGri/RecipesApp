package com.example.recipesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipesapp.data.DbRecipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: DbRecipes)

    @Delete
    suspend fun delete(recipe: DbRecipes)

    @Query("SELECT * FROM DbRecipes")
    fun getAllRecipes(): LiveData<List<DbRecipes>>
}