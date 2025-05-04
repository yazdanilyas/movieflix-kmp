package com.movieflix.app.models.playing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingResult(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Long>,

    @SerialName("id")
    val id: Long,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("title")
    val title: String,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long
)
