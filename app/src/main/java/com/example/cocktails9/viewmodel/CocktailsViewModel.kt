package com.example.cocktails9.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.model.Result
import com.example.cocktails9.repository.CocktailsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CocktailsViewModel(private val cocktailsRepo: CocktailsRepository) : ViewModel() {
    val getCocktailsList: MutableLiveData<Response<Result>> = MutableLiveData()

    init {
        viewModelScope.launch() {
            getCocktailsList.value = cocktailsRepo.getResult()
        }
    }
}