package com.albar.theater.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.albar.theater.core.domain.usecase.TheaterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val theaterUseCase: TheaterUseCase) : ViewModel() {
    private val channel = ConflatedBroadcastChannel<String>()

    fun setQuery(keywords: String) {
        channel.offer(keywords)
    }

    val getSearchResult = channel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            theaterUseCase.getSearch(it)
        }.asLiveData()
}