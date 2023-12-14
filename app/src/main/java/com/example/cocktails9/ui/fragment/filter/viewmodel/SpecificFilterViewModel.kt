package com.example.cocktails9.ui.fragment.filter.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.R
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Resource
import com.example.cocktails9.data.repository.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecificFilterViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
    private val resources: Resources
) : ViewModel() {
    private val _getFilterList: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val getFilterList: LiveData<Resource<List<String>>> get() = _getFilterList

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _getFilterList.value = Resource.Error(exception.message.toString())
    }

    private var searchJob: Job? = null

    fun getFilterList(params: Map<String, String>) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {

            _getFilterList.value = Resource.Loading(true)

            val response = filterRepository.getCategoryResponse(params)

            if (response.isSuccessful) {
                val categories = response.body()?.list ?: emptyList()
                _getFilterList.value = Resource.Success(categoriesToStrings(categories))
            } else {
                _getFilterList.value = Resource.Error(
                    resources.getString(
                        R.string.cocktails_error
                    )
                )
            }
            _getFilterList.value = Resource.Loading(false)
        }
    }

    private fun categoriesToStrings(categories: List<Category>): List<String> {
        return categories.map { category ->
            listOfNotNull(
                category.alcoholic,
                category.category,
                category.glass,
                category.ingredient
            )
        }.flatten()
    }
}