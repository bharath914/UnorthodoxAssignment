package com.example.unorthodoxassignment.presentation.states

import com.example.unorthodoxassignment.data.entity.books.BooksEntity

data class BooksListState(
    val isLoading: Boolean = false,
    val response: BooksEntity = BooksEntity(),
    val error: String = ""
)