package com.example.cocktails9.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktails9.repository.CocktailsRepository

class CocktailsViewModelFactory(private val cocktailsRepository: CocktailsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailsViewModel(cocktailsRepository) as T
    }
}