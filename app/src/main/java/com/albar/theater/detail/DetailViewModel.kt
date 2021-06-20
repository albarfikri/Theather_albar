package com.albar.theater.detail

import androidx.lifecycle.ViewModel
import com.albar.theater.core.domain.model.MovieModel
import com.albar.theater.core.domain.usecase.TheaterUseCase

class DetailViewModel(private val theaterUseCase: TheaterUseCase) : ViewModel() {
    fun setFavMovie(movie: MovieModel, newStatus: Boolean) {
        theaterUseCase.setFavMovie(movie, newStatus)
    }
}