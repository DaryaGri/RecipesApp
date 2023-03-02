package com.example.recipesapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.DbRecipes
import com.example.recipesapp.data.Recipe
import com.example.recipesapp.data.RecipesResponse
import com.example.recipesapp.repository.LocalRecipesRepository
import com.example.recipesapp.repository.RecipesRepository
import com.example.recipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val localRecipesRepository: LocalRecipesRepository
) : ViewModel() {

    val recipesData: MutableLiveData<Resource<RecipesResponse>> = MutableLiveData()
    val searchData: MutableLiveData<Resource<RecipesResponse>> = MutableLiveData()

    init {
        getRecipes()
    }

    fun getRecipes() = viewModelScope.launch {
        recipesData.postValue(Resource.Loading())
        val response = recipesRepository.getRandomRecipes()
        recipesData.postValue(handleRecipesResponse(response))
    }

    fun searchRecipes(searchQuery: String) = viewModelScope.launch {
        searchData.postValue(Resource.Loading())
        val response = recipesRepository.getSearchRecipes(searchQuery)
        searchData.postValue(handleSearchResponse(response))

    }

    private fun handleRecipesResponse(response: Response<RecipesResponse>): Resource<RecipesResponse> {
        return responseFromNetwork(response)
    }

    private fun handleSearchResponse(response: Response<RecipesResponse>): Resource<RecipesResponse> {
        return responseFromNetwork(response)
    }

    private fun responseFromNetwork(response: Response<RecipesResponse>): Resource<RecipesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveRecipe(recipe: Recipe) {
        val recipeToInsert = setRecipeToInsert(recipe)
        viewModelScope.launch {
            localRecipesRepository.insertRecipe(recipeToInsert)
        }
    }

    fun getFavouriteRecipes() = localRecipesRepository.getFavouriteRecipes()

    fun deleteRecipe(recipe: Recipe) {
        val recipeToInsert = setRecipeToInsert(recipe)
        viewModelScope.launch {
            localRecipesRepository.deleteRecipe(recipeToInsert)
        }
    }

    private fun setRecipeToInsert(recipe: Recipe): DbRecipes {
        return DbRecipes(
            title = recipe.title,
            summary = recipe.summary,
            instructions = recipe.instructions,
            sourceUrl = recipe.sourceUrl,
            image = recipe.image
        )
    }
}