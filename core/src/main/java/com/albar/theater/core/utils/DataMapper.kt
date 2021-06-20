package com.albar.theater.core.utils

import com.albar.theater.core.data.source.local.entity.MovieEntity
import com.albar.theater.core.data.source.remote.response.MoviesReponse
import com.albar.theater.core.domain.model.MovieModel

object DataMapper {
    fun mapResponseToEntities(input: List<MoviesReponse>): List<MovieEntity> {
        val movielist = ArrayList<MovieEntity>()
        input.map {
            it.apply {
                val movie = MovieEntity(
                    id,
                    backdropPath,
                    originalLanguage,
                    originalTitle,
                    overview,
                    popularity,
                    posterPath,
                    releaseDate,
                    title,
                    voteAverage,
                    voteCount,
                    isFavorite = false
                )
                movielist.add(movie)
            }
        }
        return movielist
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieModel> {
        return input.map {
            with(it) {
                MovieModel(
                    id,
                    backdropPath,
                    originalLanguage,
                    originalTitle,
                    overview,
                    popularity,
                    posterPath,
                    releaseDate,
                    title,
                    voteAverage,
                    voteCount,
                    isFavorite = isFavorite
                )
            }
        }
    }

    fun mapDomainToEntities(input: MovieModel): MovieEntity {
        return with(input) {
            MovieEntity(
                id,
                backdropPath,
                originalLanguage,
                originalTitle,
                overview,
                popularity,
                posterPath,
                releaseDate,
                title,
                voteAverage,
                voteCount,
                isFavorite = isFavorite
            )
        }
    }
}