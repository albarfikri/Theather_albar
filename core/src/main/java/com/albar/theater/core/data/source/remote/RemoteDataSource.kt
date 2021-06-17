package com.albar.theater.core.data.source.remote

import com.albar.theater.core.BuildConfig
import com.albar.theater.core.data.source.remote.network.ApiResponse
import com.albar.theater.core.data.source.remote.network.ApiService
import com.albar.theater.core.data.source.remote.response.MoviesReponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    private val apiKey = BuildConfig.API_KEY

    // only using suspend without callback as flow has supported retrofit
    suspend fun getAllMovies(): Flow<ApiResponse<List<MoviesReponse>>> {
        return flow {
            try {
                val response = apiService.getAllMovies(apiKey)
                val dataMoviesArray = response.results
                if (dataMoviesArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
            // flowOn purposes for moving to the IO Thread
        }.flowOn(Dispatchers.IO)
    }
}