package com.movieflix.app.models.playing

import kotlinx.serialization.Serializable

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)
