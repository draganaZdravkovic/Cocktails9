package com.example.cocktails9.ui.fragment.favorites.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.FavoritesItem
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) :
    ViewModel() {
    @RequiresApi(Build.VERSION_CODES.N)
    val favoritesListLiveData = repository.getFavorites().map {
        sortCocktailsToCategories(it)
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun sortCocktailsToCategories(favorites: List<Cocktails>): List<FavoritesItem> {
        val categoryMap: MutableMap<String, MutableList<Cocktails>> = mutableMapOf()

        favorites.forEach { cocktail ->
            val category = cocktail.category ?: ""
            if (!categoryMap.containsKey(category)) {
                categoryMap[category] = mutableListOf()
            }
            categoryMap[category]?.add(cocktail)
        }

        val favItems: MutableList<FavoritesItem> = mutableListOf()

        categoryMap.forEach { (category, cocktailsInCategory) ->
            favItems.add(Category(category = category))
            favItems.addAll(cocktailsInCategory)
        }

        return favItems
    }
}