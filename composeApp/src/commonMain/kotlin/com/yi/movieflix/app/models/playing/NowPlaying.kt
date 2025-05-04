package com.movieflix.app.models.playing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlaying(
    val dates: Dates,
    val page: Long,

    val results: List<NowPlayingResult>,
    @SerialName("total_pages")
    val totalPages: Long,
    @SerialName("total_results")
    val totalResults: Long,
)