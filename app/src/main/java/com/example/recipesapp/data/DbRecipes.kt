package com.example.recipesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbRecipes(
    @ColumnInfo var title: String?,
    @ColumnInfo var summary: String?,
    @ColumnInfo var instructions: String,
    @ColumnInfo var sourceUrl: String? = null,
    @ColumnInfo var image: String? = null,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
)
