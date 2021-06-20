package com.albar.theater.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albar.theater.core.domain.usecase.TheaterUseCase

class MoviesViewModel(private val theaterUseCase: TheaterUseCase) : ViewModel() {
    val movie = theaterUseCase.getAllMovies().asLiveData()
}