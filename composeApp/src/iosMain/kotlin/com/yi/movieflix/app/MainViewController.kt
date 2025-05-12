package com.yi.movieflix.app

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import com.movieflix.app.data.remote.createHttpClient
import com.yi.movieflix.app.data.remote.MovieClient
import com.yi.movieflix.app.navigation.AppNavigationHost
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    val navController = rememberNavController()
    val httpClient = MovieClient(createHttpClient(Darwin.create()))
    AppNavigationHost(navController, httpClient)
}