package com.albar.theater.core.data.source

import com.albar.theater.core.data.source.local.LocalDataSource
import com.albar.theater.core.data.source.remote.RemoteDataSource
import com.albar.theater.core.data.source.remote.network.ApiResponse
import com.albar.theater.core.data.source.remote.response.MoviesReponse
import com.albar.theater.core.domain.model.MovieModel
import com.albar.theater.core.domain.repository.ITheaterRepository
import com.albar.theater.core.utils.AppExecutors
import com.albar.theater.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TheaterRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITheaterRepository {
    override fun getAllMovies(): Flow<Resource<List<MovieModel>>> =
        object : NetworkBoundResource<List<MovieModel>, List<MoviesReponse>>() {

            override fun loadFromDb(): Flow<List<MovieModel>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesReponse>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MoviesReponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getFavMovies(): Flow<List<MovieModel>> {
        return localDataSource.getFavMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavMovie(movieModel: MovieModel, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntities(movieModel)
        appExecutors.diskIo().execute { localDataSource.setFavMovie(movieEntity, state) }
    }

}