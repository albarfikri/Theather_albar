package com.albar.theater.core.domain.usecase

import com.albar.theater.core.data.source.Resource
import com.albar.theater.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface TheaterUseCase {
    fun getAllMovies(): Flow<Resource<List<MovieModel>>>
    fun getFavMovies(): Flow<List<MovieModel>>
    fun setFavMovie(movieModel: MovieModel, state: Boolean)
}