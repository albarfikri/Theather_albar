package com.albar.theater.core.domain.usecase

import com.albar.theater.core.data.source.Resource
import com.albar.theater.core.domain.model.MovieModel
import com.albar.theater.core.domain.repository.ITheaterRepository
import kotlinx.coroutines.flow.Flow

class TheaterInteractor(private val theaterRepository: ITheaterRepository) : TheaterUseCase {
    override fun getAllMovies(): Flow<Resource<List<MovieModel>>> = theaterRepository.getAllMovies()

    override fun getFavMovies(): Flow<List<MovieModel>> = theaterRepository.getFavMovies()

    override fun setFavMovie(movieModel: MovieModel, state: Boolean) =
        theaterRepository.setFavMovie(movieModel, state)

    override fun getSearch(keywords: String): Flow<List<MovieModel>> =
        theaterRepository.getSearch(keywords)
}