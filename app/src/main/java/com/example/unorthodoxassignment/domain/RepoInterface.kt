package com.example.unorthodoxassignment.domain

import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.data.entity.books.SpecificBook

interface RepoInterface {
    suspend fun getBooksResponse(): BooksEntity

    suspend fun getSpecificBook(id: String): SpecificBook

    suspend fun getSearchResult(query: String): BooksEntity
}