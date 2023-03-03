package com.example.recipesapp.data

import java.io.Serializable

data class ExtendedIngredient(
   var aisle: String?,
   var amount: String?,
   var consistency: String?,
   var id: Int?,
   var image: String?,
   var measures: Measures?,
   var meta: List<String>?,
   var name: String?,
   var nameClean: String?,
   var original: String?,
   var originalName: String?,
   var unit: String?
) : Serializable