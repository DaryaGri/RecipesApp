package com.example.recipesapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.RecipesResponse
import com.example.recipesapp.repository.RecipesRepository
import com.example.recipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    val recipesData: MutableLiveData <Resource<RecipesResponse>> = MutableLiveData()

    init {
        getRecipes()
    }

    private fun getRecipes() = viewModelScope.launch {
        recipesData.postValue(Resource.Loading())
        val response = recipesRepository.getRandomRecipes()
        recipesData.postValue(handleRecipesResponse(response))
    }

    private fun handleRecipesResponse(response: Response<RecipesResponse>) : Resource<RecipesResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}