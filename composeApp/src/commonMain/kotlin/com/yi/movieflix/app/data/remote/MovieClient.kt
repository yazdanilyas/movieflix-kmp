package com.movieflix.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MovieClient(private val httpClient: HttpClient) {

    suspend fun getMovies(): HttpResponse {

        val response = httpClient.get(
            urlString = "https://api.themoviedb.org/3/movie/now_playing"
        ) {
            parameter("page", 1)
        }
        return response
    }
}