package com.example.cocktails9.ui.fragment.details.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.R
import com.example.cocktails9.data.model.CocktailDetails
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.data.repository.CocktailsRepository
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsDetailsViewModel @Inject constructor(
    private val cocktailsRepo: CocktailsRepository,
    private val favoritesRepo: FavoritesRepository,
    private val resources: Resources,
) : ViewModel() {

    private var _getCocktailDetails: MutableLiveData<Resource<CocktailDetails>> = MutableLiveData()
    val getCocktailDetails: LiveData<Resource<CocktailDetails>> get() = _getCocktailDetails

    private lateinit var _isFavorite: LiveData<Boolean>
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun getIsFavorite(cocktailID: String, userEmail: String) {
        _isFavorite = favoritesRepo.getIsFavorite(cocktailID, userEmail)
    }

    fun getCocktailDetails(cocktailID: String) {
        viewModelScope.launch {
            _getCocktailDetails.value = Resource.Loading(true)

            val response = cocktailsRepo.getCocktailDetails(cocktailID)

            if (response.isSuccessful) {
                val cocktails = response.body()?.list ?: emptyList()
                val cocktail = cocktails[0]
                cocktail.ingredientMeasureString = createIngredientMeasureString(cocktail)
                _getCocktailDetails.value = Resource.Success(cocktail)
            } else {
                _getCocktailDetails.value = Resource.Error(
                    resources.getString(
                        R.string.cocktails_error
                    )
                )
            }
            _getCocktailDetails.value = Resource.Loading(false)
        }
    }

    private fun createIngredientMeasureString(cocktail: CocktailDetails): String {
        val ingredientMeasureList = mutableListOf<String>()

        for (i in 1..15) {
            val ingredient = getIngredientValue(cocktail, i)
            val measure = getMeasureValue(cocktail, i)

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                val combinedString = "$ingredient ($measure) "
                ingredientMeasureList.add(combinedString)
            }

            if (!ingredient.isNullOrBlank() && measure.isNullOrBlank()) {
                val combinedString = "$ingredient"
                ingredientMeasureList.add(combinedString)
            }
        }

        return ingredientMeasureList.joinToString("\n")
    }

    private fun getIngredientValue(cocktail: CocktailDetails, index: Int): String? {
        return when (index) {
            1 -> cocktail.ingredient1
            2 -> cocktail.ingredient2
            3 -> cocktail.ingredient3
            4 -> cocktail.ingredient4
            5 -> cocktail.ingredient5
            6 -> cocktail.ingredient6
            7 -> cocktail.ingredient7
            8 -> cocktail.ingredient8
            9 -> cocktail.ingredient9
            10 -> cocktail.ingredient10
            11 -> cocktail.ingredient11
            12 -> cocktail.ingredient12
            13 -> cocktail.ingredient13
            14 -> cocktail.ingredient14
            15 -> cocktail.ingredient15
            else -> null
        }
    }

    private fun getMeasureValue(cocktail: CocktailDetails, index: Int): String? {
        return when (index) {
            1 -> cocktail.measure1
            2 -> cocktail.measure2
            3 -> cocktail.measure3
            4 -> cocktail.measure4
            5 -> cocktail.measure5
            6 -> cocktail.measure6
            7 -> cocktail.measure7
            8 -> cocktail.measure8
            9 -> cocktail.measure9
            10 -> cocktail.measure10
            11 -> cocktail.measure11
            12 -> cocktail.measure12
            13 -> cocktail.measure13
            14 -> cocktail.measure14
            15 -> cocktail.measure15
            else -> null
        }
    }
}