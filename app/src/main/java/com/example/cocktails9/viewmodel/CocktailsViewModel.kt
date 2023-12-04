package com.example.cocktails9.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.model.Resource
import com.example.cocktails9.model.Result
import com.example.cocktails9.repository.CocktailsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CocktailsViewModel(private val cocktailsRepo: CocktailsRepository) : ViewModel() {
    private val _getCocktailsList: MutableLiveData<Resource<Response<Result>>> = MutableLiveData()
    val getCocktailsList: LiveData<Resource<Response<Result>>> get() = _getCocktailsList

    fun getCocktails() {
        _getCocktailsList.value = Resource.Loading(true)

        viewModelScope.launch() {
            try {
                _getCocktailsList.value = Resource.Success(cocktailsRepo.getResult())
            } catch (e: Exception) {
                _getCocktailsList.value = Resource.Error(e.message.toString())
            } finally {
                _getCocktailsList.value = Resource.Loading(false)
            }
        }
    }
}