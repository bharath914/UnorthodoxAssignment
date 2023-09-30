package com.example.unorthodoxassignment.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unorthodoxassignment.common.Resource
import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.data.entity.books.SpecificBook
import com.example.unorthodoxassignment.presentation.states.BooksListState
import com.example.unorthodoxassignment.presentation.states.SpecificBookState
import com.example.unorthodoxassignment.useCases.GetSpecificBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SpecificBookViewModel @Inject constructor(
    private val getSpecificBookUseCase: GetSpecificBookUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _book = mutableStateOf(SpecificBookState())
    val book: State<SpecificBookState> = _book

    init {
        savedStateHandle.get<String>("id")?.let { id ->
            getBooksResponse(id)
        }
    }

    private fun getBooksResponse(id: String) {
        getSpecificBookUseCase(id).onEach { result ->

            when (result) {
                is Resource.Success -> {
                    _book.value = SpecificBookState(specificBook = result.data ?: SpecificBook())
                }

                is Resource.Error -> {
                    _book.value = SpecificBookState(error = result.message ?: "Unexpected Error")

                }

                is Resource.Loading -> {
                    _book.value = SpecificBookState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}