package com.example.unorthodoxassignment.presentation.states

import com.example.unorthodoxassignment.data.entity.books.SpecificBook

data class SpecificBookState(
    val isLoading: Boolean = false,
    val specificBook: SpecificBook? = null,
    val error: String = ""
)