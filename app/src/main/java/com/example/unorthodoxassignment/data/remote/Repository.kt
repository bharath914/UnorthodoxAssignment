package com.example.unorthodoxassignment.data.remote

import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.data.entity.books.SpecificBook
import com.example.unorthodoxassignment.domain.RepoInterface
import javax.inject.Inject

class Repository @Inject constructor(
    private val booksApi: BooksApi
) : RepoInterface {
    override suspend fun getBooksResponse(): BooksEntity {
        return booksApi.getRecentBooks()
    }

    override suspend fun getSpecificBook(id: String): SpecificBook {
        return booksApi.getClickedBook(id)
    }

    override suspend fun getSearchResult(query: String): BooksEntity {
        return booksApi.getSearchResults(query)
    }

}