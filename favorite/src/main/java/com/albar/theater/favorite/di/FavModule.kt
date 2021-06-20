package com.albar.theater.favorite.di

import com.albar.theater.favorite.fav.FavViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel{
        FavViewModel(get())
    }
}