package com.example.recipesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value = ["title"], unique = true)])
data class DbRecipes(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo var summary: String?,
    @ColumnInfo var instructions: String?,
    @ColumnInfo var sourceUrl: String? = null,
    @ColumnInfo var image: String? = null,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
) : Serializable
