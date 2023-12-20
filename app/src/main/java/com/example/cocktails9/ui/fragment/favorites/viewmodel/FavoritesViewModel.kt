package com.example.cocktails9.ui.fragment.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.FavoritesItem
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) :
    ViewModel() {

    private lateinit var _getFavoritesListLiveData: LiveData<List<FavoritesItem>>
    val favoritesListLiveData: LiveData<List<FavoritesItem>> get() = _getFavoritesListLiveData

    fun getFavorites(userEmail: String) {
        _getFavoritesListLiveData = repository.getFavorites(userEmail).map {
            sortCocktailsToCategories(it)
        }
    }

    private fun sortCocktailsToCategories(favorites: List<Cocktails>): List<FavoritesItem> {
        val categoryMap: MutableMap<String, MutableList<Cocktails>> = mutableMapOf()

        favorites.forEach { cocktail ->
            val category = cocktail.alcoholic ?: ""
            if (!categoryMap.containsKey(category)) {
                categoryMap[category] = mutableListOf()
            }
            categoryMap[category]?.add(cocktail)
        }

        val favItems: MutableList<FavoritesItem> = mutableListOf()

        categoryMap.forEach { (category, cocktailsInCategory) ->
            favItems.add(Category(alcoholic = category))
            favItems.addAll(cocktailsInCategory)
        }

        return favItems
    }
}