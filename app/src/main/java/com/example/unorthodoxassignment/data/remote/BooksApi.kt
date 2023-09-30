package com.example.unorthodoxassignment.data.remote

import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.data.entity.books.SpecificBook
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("recent")
    suspend fun getRecentBooks(): BooksEntity

    @GET("book/{id}")
    suspend fun getClickedBook(@Path("id") id: String): SpecificBook

    @GET("search/{query}")
    suspend fun getSearchResults(@Path("query") query: String): BooksEntity

    //
    companion object Const {
        const val BASE_URL = "https://www.dbooks.org/api/"
    }
}