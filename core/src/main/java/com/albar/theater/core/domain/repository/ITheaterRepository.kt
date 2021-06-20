package com.albar.theater.core.domain.repository

import com.albar.theater.core.data.source.Resource
import com.albar.theater.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface ITheaterRepository {
    fun getAllMovies(): Flow<Resource<List<MovieModel>>>

    fun getFavMovies(): Flow<List<MovieModel>>

    fun setFavMovie(movieModel: MovieModel, state: Boolean)

    fun getSearch(keywords: String): Flow<List<MovieModel>>
}