package com.albar.theater.di

import com.albar.theater.core.domain.usecase.TheaterInteractor
import com.albar.theater.core.domain.usecase.TheaterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<TheaterUseCase> { TheaterInteractor(get())}
}
val viewModelModule = module{
//    viewModel{}
}