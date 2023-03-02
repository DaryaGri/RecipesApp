package com.example.recipesapp.data

import java.io.Serializable

data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
) : Serializable