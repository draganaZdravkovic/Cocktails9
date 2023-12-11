package com.example.cocktails9.ui.fragment.cocktails.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.data.repository.CocktailsRepository
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(
    private val cocktailsRepo: CocktailsRepository,
    private val favoritesRepo: FavoritesRepository,
    private val resources: Resources

) : ViewModel() {
    private val _getCocktailsList: MutableLiveData<Resource<List<Cocktails>>> = MutableLiveData()
    val getCocktailsList: LiveData<Resource<List<Cocktails>>> get() = _getCocktailsList


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _getCocktailsList.value = Resource.Error(exception.message.toString())
    }

    private var searchJob: Job? = null

    companion object {
        private const val DELAY = 500L
    }

    fun getCocktails(searchQuery: String = "") {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {

            _getCocktailsList.value = Resource.Loading(true)

            if (searchQuery.isEmpty()) delay(DELAY)

            val response = cocktailsRepo.getCocktailsResponse(searchQuery)

            if (response.isSuccessful) {
                val cocktails = response.body()?.list ?: emptyList()
                val favorites = favoritesRepo.getFavorites()
                _getCocktailsList.value =
                    Resource.Success(updateFavoriteStatusInCocktails(favorites, cocktails))
            } else {
                _getCocktailsList.value = Resource.Error(
                    resources.getString(
                        R.string.cocktails_error
                    )
                )
            }
            _getCocktailsList.value = Resource.Loading(false)
        }
    }

    private fun updateFavoriteStatusInCocktails(
        favorites: List<Cocktails>?,
        cocktails: List<Cocktails>
    ): List<Cocktails> {
        val mergedCocktails: MutableList<Cocktails> = mutableListOf()

        favorites?.forEach { favoriteItem ->
            val matchingCocktail = cocktails.find { it.id == favoriteItem.id }
            if (matchingCocktail != null) {
                mergedCocktails.add(favoriteItem.copy(isFavorite = true))
            }
        }

        val cocktailsIdsInFavorites = favorites?.map { it.id }
        val remainingCocktails = cocktails.filter { it.id !in cocktailsIdsInFavorites.orEmpty() }
        mergedCocktails.addAll(remainingCocktails)

        return mergedCocktails
    }
}