package com.example.recipesapp.data

import java.io.Serializable

data class Equipment(
    val id: Int?,
    val image: String?,
    val localizedName: String?,
    val name: String?,
    val temperature: Temperature?
) : Serializable