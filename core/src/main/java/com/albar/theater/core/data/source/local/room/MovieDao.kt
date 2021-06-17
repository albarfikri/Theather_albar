package com.albar.theater.core.data.source.local.room

import androidx.room.*
import com.albar.theater.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies where isFavorite=1")
    fun getFavMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(movie:List<MovieEntity>)

    @Update
    fun updateFavMovie(movie: MovieEntity)
}