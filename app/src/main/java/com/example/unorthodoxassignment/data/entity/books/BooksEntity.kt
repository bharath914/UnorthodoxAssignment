package com.example.unorthodoxassignment.data.entity.books

data class BooksEntity(
    val books: List<Book> = emptyList(),
    val status: String="",
    val total: Int=0
)