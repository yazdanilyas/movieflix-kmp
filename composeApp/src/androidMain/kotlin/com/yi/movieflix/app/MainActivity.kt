package com.yi.movieflix.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.movieflix.app.data.remote.createHttpClient
import com.yi.movieflix.app.data.remote.MovieClient
import com.yi.movieflix.app.navigation.AppNavigationHost
import com.yi.movieflix.app.ui.splash.Splash
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val httpClient = MovieClient(createHttpClient(OkHttp.create()))
            AppNavigationHost(navController, httpClient)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    Splash(null)
//    MainScreen(null, null)
}