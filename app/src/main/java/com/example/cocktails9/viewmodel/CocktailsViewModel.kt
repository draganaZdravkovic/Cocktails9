package com.example.cocktails9.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.model.Cocktails
import com.example.cocktails9.model.Resource
import com.example.cocktails9.repository.CocktailsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CocktailsViewModel : ViewModel() {
    private val _getCocktailsList: MutableLiveData<Resource<List<Cocktails>>> = MutableLiveData()
    val getCocktailsList: LiveData<Resource<List<Cocktails>>> get() = _getCocktailsList
    private val cocktailsRepo: CocktailsRepository = CocktailsRepository()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _getCocktailsList.value = Resource.Error(exception.message.toString())
    }

    private var searchJob: Job? = null
    private val _delay = 500L

    fun getCocktails(searchQuery: String = "") {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {
            _getCocktailsList.value = Resource.Loading(true)

            if (searchQuery.isEmpty()) delay(_delay)

            val response = cocktailsRepo.getResult(searchQuery)

            if (response.isSuccessful) {
                val cocktails = response.body()?.list ?: emptyList()
                if (cocktails.isEmpty())
                    _getCocktailsList.value = Resource.Empty(cocktails, "No cocktails found.")
                else
                    _getCocktailsList.value = Resource.Success(cocktails)
            } else {
                _getCocktailsList.value = Resource.Error("Error occurred while fetching data!")
            }
            _getCocktailsList.value = Resource.Loading(false)
        }
    }
}