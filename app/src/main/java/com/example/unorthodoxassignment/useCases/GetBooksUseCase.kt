package com.example.unorthodoxassignment.useCases

import com.example.unorthodoxassignment.common.Resource
import com.example.unorthodoxassignment.data.entity.books.BooksEntity
import com.example.unorthodoxassignment.data.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<BooksEntity>> = flow {

        try {
            emit(Resource.Loading())
            val response = repository.getBooksResponse()
            emit(Resource.Success(data = response))

        }catch (e:HttpException){
            emit(Resource.Error(e.localizedMessage ?:"Unexpected Network Error"))
        }catch (e:IOException){
            emit(Resource.Error("Servers Are Busy Right Now"))
        }
    }
}