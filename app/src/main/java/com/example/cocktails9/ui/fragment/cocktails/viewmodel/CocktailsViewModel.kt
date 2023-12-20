package com.example.cocktails9.ui.fragment.cocktails.viewmodel

import android.content.SharedPreferences
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
import com.example.cocktails9.utils.Constants
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
    private val resources: Resources,
    private val sharedPreferences: SharedPreferences
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

    fun addFavorite(cocktail: Cocktails, userEmail: String) {
        viewModelScope.launch {

            cocktail.userEmail =
                sharedPreferences.getString(Constants.EMAIL_KEY + userEmail, null)
            cocktailsRepo.insertFavorite(cocktail)
        }
    }

    fun removeFavorite(cocktail: Cocktails, userEmail: String) {
        viewModelScope.launch {
            cocktail.userEmail =
                sharedPreferences.getString(Constants.EMAIL_KEY + userEmail, null)
            cocktailsRepo.removeFavorite(cocktail)
        }
    }

    fun getCocktails(searchQuery: String = "", userEmail: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {

            _getCocktailsList.value = Resource.Loading(true)

            if (searchQuery.isEmpty()) delay(DELAY)

            val response = cocktailsRepo.getCocktailsResponse(searchQuery)

            if (response.isSuccessful) {
                val cocktails = response.body()?.list ?: emptyList()
                val favoritesId =
                    sharedPreferences.getString(Constants.EMAIL_KEY + userEmail, null)
                        ?.let { favoritesRepo.getAllFavoritesId(it) }
                if (favoritesId != null) {
                    updateFavoriteStatusInCocktails(favoritesId, cocktails)
                }
                _getCocktailsList.value = Resource.Success(cocktails)
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

    fun getCocktailsByCategory(params: Map<String, String>) {
        viewModelScope.launch(exceptionHandler) {

            _getCocktailsList.value = Resource.Loading(true)

            val response = cocktailsRepo.getCocktailsByCategory(params)

            if (response.isSuccessful) {
                val cocktails = response.body()?.list ?: emptyList()

//                Not every cocktail has it's category
                cocktails.forEach { cocktail ->
                    val alcoholic = params.values.elementAt(0)
                    if (alcoholic == "Non alcoholic" ||
                        alcoholic == "Optional alcohol" ||
                        alcoholic == "Alcoholic"
                    )
                        cocktail.alcoholic = alcoholic
                    else cocktail.alcoholic = resources.getString(R.string.other)

                }
                val favoritesId = sharedPreferences.getString(Constants.EMAIL_KEY, null)
                    ?.let { favoritesRepo.getAllFavoritesId(it) }
                if (favoritesId != null) {
                    updateFavoriteStatusInCocktails(favoritesId, cocktails)
                }
                _getCocktailsList.value = Resource.Success(cocktails)
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
        favoritesId: List<String>,
        cocktails: List<Cocktails>
    ) {
        cocktails.forEach {
            it.isFavorite = favoritesId.contains(it.id)
        }
    }
}