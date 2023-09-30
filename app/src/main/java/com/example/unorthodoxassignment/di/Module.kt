package com.example.unorthodoxassignment.di

import com.example.unorthodoxassignment.data.remote.BooksApi
import com.example.unorthodoxassignment.data.remote.Repository
import com.example.unorthodoxassignment.domain.RepoInterface
import com.example.unorthodoxassignment.useCases.GetBooksUseCase
import com.example.unorthodoxassignment.useCases.GetSearchUseCase
import com.example.unorthodoxassignment.useCases.GetSpecificBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideBooksApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(BooksApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            .create(BooksApi::class.java)
    }


    @Provides
    @Singleton
    fun provideBooksInterface(booksApi: BooksApi): RepoInterface = Repository(
        booksApi
    )

    @Provides
    @Singleton
    fun provideGetBooksListUseCase(repository: Repository) = GetBooksUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSearchUseCase(repository: Repository) = GetSearchUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSpecificUseCase(repository: Repository) = GetSpecificBookUseCase(repository)

}