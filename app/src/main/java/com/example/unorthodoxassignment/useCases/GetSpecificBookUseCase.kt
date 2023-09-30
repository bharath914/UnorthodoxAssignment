package com.example.unorthodoxassignment.useCases

import com.example.unorthodoxassignment.common.Resource
import com.example.unorthodoxassignment.data.entity.books.SpecificBook
import com.example.unorthodoxassignment.data.remote.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetSpecificBookUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: String): Flow<Resource<SpecificBook>> = flow {
        try {

            emit(Resource.Loading())
            val book = repository.getSpecificBook(id)
            emit(Resource.Success(book))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Network Error or Servers Are Busy"))
        }
    }
}