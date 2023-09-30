package com.example.unorthodoxassignment.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unorthodoxassignment.common.Resource
import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.presentation.states.BooksListState
import com.example.unorthodoxassignment.useCases.GetBooksUseCase
import com.example.unorthodoxassignment.useCases.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getSearchUseCase: GetSearchUseCase

) : ViewModel() {


    private val _booksList = MutableStateFlow(BooksListState())
    val booksList = _booksList.asStateFlow()

    private val _recentBooks = MutableStateFlow(BooksListState())
    val recentBooks = _recentBooks.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {


        getBooksResponse()

    }

    private fun getBooksResponse() {
        getBooksUseCase().onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _booksList.value = BooksListState(response = result.data ?: BooksEntity())
                    _recentBooks.tryEmit(BooksListState(response = result.data ?: BooksEntity()))
                }

                is Resource.Error -> {
                    _booksList.value = BooksListState(error = result.message ?: "Unexpected Error")

                }

                is Resource.Loading -> {
                    _booksList.value = BooksListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getSearchResult(query: String) {
        getSearchUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _booksList.value = BooksListState(response = result.data ?: BooksEntity())
                }

                is Resource.Error -> {
                    _booksList.value = BooksListState(error = result.message ?: "Unexpected Error")

                }

                is Resource.Loading -> {
                    _booksList.value = BooksListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun saveSearchTExt(text: String) {
        _searchText.tryEmit(text)
    }

    fun clearSearch() {
        _searchText.tryEmit("")
        _booksList.tryEmit(recentBooks.value)

    }
}