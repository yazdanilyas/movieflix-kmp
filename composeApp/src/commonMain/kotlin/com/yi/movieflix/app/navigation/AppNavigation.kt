package com.movieflix.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.movieflix.app.data.remote.MovieClient
import com.yi.movieflix.app.ui.MovieDetailScreen
import com.yi.movieflix.app.ui.Splash
import com.yi.movieflix.app.ui.main.MainScreen


@Composable
fun AppNavigationHost(navController: NavHostController, client: MovieClient) {

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH_SCREEN.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Routes.SPLASH_SCREEN.name) {
            Splash(navController)
        }
        composable(route = Routes.MAIN_SCREEN.name) {
            MainScreen(navController, client)
        }
        composable(route = Routes.MOVIE_DETAIL_SCREEN.name) {
            MovieDetailScreen(navController)
        }
    }

}