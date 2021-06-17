package com.albar.theater.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    var id: Int? = null,
    var backdropPath: String? = null,
    var originalLanguage: String? = null,
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    var voteAverage: Double? = null,
    var voteCount: Int? = null,
    var isFavorite: Boolean = false
) : Parcelable