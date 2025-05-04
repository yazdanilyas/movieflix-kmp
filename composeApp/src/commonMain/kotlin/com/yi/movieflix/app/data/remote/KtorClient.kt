package com.movieflix.app.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL

        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
        defaultRequest {
            header("accept", "application/json")
            header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNDQ2ZGM2ZDc3NmFiNzBmOTc2NzE3YTNlZDkyYjNlYyIsIm5iZiI6MTc0MTg1MDUyNC4wMDMsInN1YiI6IjY3ZDI4NzliNjQ0NDM5ODRiMTFlMmFhNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EXj9AKh8qHJCjFNSZeJiDmnFC2vHg15sB3QfUEHkIQ8"
            )
        }
    }
}