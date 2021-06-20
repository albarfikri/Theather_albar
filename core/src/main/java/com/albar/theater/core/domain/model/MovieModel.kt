package com.albar.theater.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    var id: Int,
    var backdropPath: String,
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var popularity: Double,
    var posterPath: String,
    var releaseDate: String,
    var title: String,
    var voteAverage: Double,
    var voteCount: Int,
    var isFavorite: Boolean = false
) : Parcelable