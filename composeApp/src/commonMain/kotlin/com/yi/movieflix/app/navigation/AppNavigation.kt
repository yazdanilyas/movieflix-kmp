package com.yi.movieflix.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.movieflix.app.navigation.Routes
import com.yi.movieflix.app.data.remote.MovieClient
import com.yi.movieflix.app.ui.MovieDetailScreen
import com.yi.movieflix.app.ui.favorites.FavoritesScreen
import com.yi.movieflix.app.ui.main.MainScreen
import com.yi.movieflix.app.ui.splash.Splash


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
        composable(route = Routes.FAVORITES_SCREEN.name) {
            FavoritesScreen(navController)
        }
    }

}