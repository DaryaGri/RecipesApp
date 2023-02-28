package com.example.recipesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesapp.data.DbRecipes

@Database(entities = [DbRecipes::class], version = 1)
abstract class RecipesAppDataBase : RoomDatabase() {

    abstract fun getRecipesDao(): RecipesDao
}