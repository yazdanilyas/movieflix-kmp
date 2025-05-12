package com.yi.movieflix.app.models.playing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResult(
    @SerialName("dates")
    val dates: Dates? = null,
    val page: Long,
    val results: List<MovieDetail>,
    @SerialName("total_pages")
    val totalPages: Long,
    @SerialName("total_results")
    val totalResults: Long,
)