package com.albar.theater.favorite.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albar.theater.core.domain.usecase.TheaterUseCase

class FavViewModel(theaterUseCase: TheaterUseCase) : ViewModel() {
    val favMovie = theaterUseCase.getFavMovies().asLiveData()
}