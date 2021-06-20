package com.albar.theater.core.data.source.local

import com.albar.theater.core.data.source.local.entity.MovieEntity
import com.albar.theater.core.data.source.local.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getFavMovie(): Flow<List<MovieEntity>> = movieDao.getFavMovies()

    suspend fun insertMovies(movieEntity: List<MovieEntity>) =
        movieDao.insertFavMovie(movieEntity)

    fun setFavMovie(movieEntity: MovieEntity, newState: Boolean) {
        movieEntity.isFavorite = newState
        movieDao.updateFavMovie(movieEntity)
    }

    fun getSearch(search: String): Flow<List<MovieEntity>> {
        return movieDao.getSearch(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }
}