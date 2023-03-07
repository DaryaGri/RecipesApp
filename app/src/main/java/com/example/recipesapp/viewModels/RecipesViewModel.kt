package com.example.recipesapp.viewModels

import androidx.lifecycle.LiveData
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

    private val _recipesData = MutableLiveData<Resource<RecipesResponse>>()
    val recipesData: LiveData<Resource<RecipesResponse>> get() = _recipesData

    private val _searchData: MutableLiveData<Resource<RecipesResponse>> = MutableLiveData()
    val searchData: LiveData<Resource<RecipesResponse>> get() = _searchData

    init {
        getRecipes()
    }

    fun getRecipes() = viewModelScope.launch {
        _recipesData.postValue(Resource.Loading())
        val response = recipesRepository.getRandomRecipes()
        _recipesData.postValue(responseFromNetwork(response))
    }

    fun searchRecipes(searchQuery: String) = viewModelScope.launch {
        _searchData.postValue(Resource.Loading())
        val response = recipesRepository.getSearchRecipes(searchQuery)
        _searchData.postValue(responseFromNetwork(response))

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
            val elementToDelete = localRecipesRepository.getByTitle(recipeToInsert)
            localRecipesRepository.deleteRecipe(elementToDelete)
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