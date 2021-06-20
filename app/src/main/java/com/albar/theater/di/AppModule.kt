package com.albar.theater.di

import com.albar.theater.core.domain.usecase.TheaterInteractor
import com.albar.theater.core.domain.usecase.TheaterUseCase
import com.albar.theater.detail.DetailViewModel
import com.albar.theater.movies.MoviesViewModel
import com.albar.theater.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TheaterUseCase> { TheaterInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}