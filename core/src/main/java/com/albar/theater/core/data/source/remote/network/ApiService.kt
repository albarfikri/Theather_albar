package com.albar.theater.core.data.source.remote.network

import com.albar.theater.core.data.source.remote.response.ListMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String
    ): ListMoviesResponse
}