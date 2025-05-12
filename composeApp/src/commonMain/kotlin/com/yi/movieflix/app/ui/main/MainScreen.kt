package com.yi.movieflix.app.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.movieflix.app.navigation.Routes
import com.yi.movieflix.app.data.remote.MovieClient
import com.yi.movieflix.app.models.playing.MovieDetail
import com.yi.movieflix.app.models.playing.MoviesResult
import com.yi.movieflix.app.ui.favorites.FavoritesScreen
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import movieflix_kmp.composeapp.generated.resources.Res
import movieflix_kmp.composeapp.generated.resources.app_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun previewMainScreen() {
    MainScreen(null, null)
}

@Composable
fun MainScreen(mainNavController: NavController?, client: MovieClient?) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {},
        bottomBar = {
            BottomBarNavigation(navController)
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.MAIN_SCREEN.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.MAIN_SCREEN.name) {
                MainScreenContent(mainNavController, client)
            }
            composable(Routes.FAVORITES_SCREEN.name) {
                FavoritesScreen(mainNavController)
            }
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val homeSelection = remember { mutableStateOf(true) }
    val favSelection = remember { mutableStateOf(false) }
    BottomNavigation(backgroundColor = Color.LightGray) {
        BottomNavigationItem(
            onClick = {
                favSelection.value = false
                homeSelection.value = true
                navController.navigate(Routes.MAIN_SCREEN.name) {
                    popUpTo(Routes.MAIN_SCREEN.name) { inclusive = true }
                }
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = homeSelection.value,
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.DarkGray
        )

        BottomNavigationItem(
            onClick = {
                favSelection.value = true
                homeSelection.value = false
                navController.navigate(Routes.FAVORITES_SCREEN.name) {
                    popUpTo(Routes.FAVORITES_SCREEN.name) { inclusive = true }
                }
            },
            icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = favSelection.value,
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.DarkGray
        )
    }
}

@Composable
fun MainScreenContent(navController: NavController?, client: MovieClient?) {
    val scope = rememberCoroutineScope()
    val nowPlayingMovieList = remember { mutableStateOf<List<MovieDetail>>(emptyList()) }
    val topRatedMovieList = remember { mutableStateOf<List<MovieDetail>>(emptyList()) }
    val upComingMovieList = remember { mutableStateOf<List<MovieDetail>>(emptyList()) }
    val popularMovieList = remember { mutableStateOf<List<MovieDetail>>(emptyList()) }

    // Fetching movies in a coroutine
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val nowPlayingDeferred = async {
                val result = client?.getMovies("now_playing")
                val res = result?.body() as MoviesResult
                nowPlayingMovieList.value = res.results
            }
            nowPlayingDeferred.await()
            val topRatedDeferred = async {
                val result = client?.getMovies("top_rated")
                val res = result?.body() as MoviesResult
                topRatedMovieList.value = res.results
            }
            topRatedDeferred.await()
            val upcomingDeferred = async {
                val result = client?.getMovies("upcoming")
                val res = result?.body() as MoviesResult
                upComingMovieList.value = res.results
            }
            upcomingDeferred.await()
            val popularDeferred = async {
                val result = client?.getMovies("popular")
                val res = result?.body() as MoviesResult
                popularMovieList.value = res.results
            }
            popularDeferred.await()
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ) {
        Spacer(Modifier.fillMaxWidth().height(10.dp))
        Text(
            "Now Playing",
            Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(nowPlayingMovieList.value.take(5)) { movie ->
                MovieItem(
                    movie,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
                )
            }
            item {
                SeeMoreItem(
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.DarkGray, RoundedCornerShape(15.dp)).clickable { }
                )
            }
        }
        Text(
            "UpComing",
            Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(upComingMovieList.value.take(5)) { movie ->
                MovieItem(
                    movie,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
                )
            }
            item {
                SeeMoreItem(
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.DarkGray, RoundedCornerShape(15.dp)).clickable { }
                )
            }
        }
        Text(
            "Popular",
            Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(popularMovieList.value.take(5)) { movie ->
                MovieItem(
                    movie,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
                )
            }
            item {
                SeeMoreItem(
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.DarkGray, RoundedCornerShape(15.dp)).clickable { }
                )
            }
        }
        Text(
            "Top Rated",
            Modifier.padding(start = 15.dp, top = 10.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(topRatedMovieList.value.take(5)) { movie ->
                MovieItem(
                    movie,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
                )
            }
            item {
                SeeMoreItem(
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.DarkGray, RoundedCornerShape(15.dp)).clickable { }
                )
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieDetail?, modifier: Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Card(modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                loadingImage(movie, Modifier.fillMaxWidth().weight(1f))

                movie?.title?.let {
                    Text(
                        it,
                        Modifier.padding(top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

        }
    }
}

@Composable
fun SeeMoreItem(modifier: Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Card(modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "See More",
                    Modifier.padding(top = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Composable
fun loadingImage(movie: MovieDetail?, modifier: Modifier) {
    val posterPath = movie?.posterPath
    val imageUrl = if (posterPath?.startsWith("/") == true) {
        "https://image.tmdb.org/t/p/original${posterPath}"
    } else if (posterPath != null) {
        "https://image.tmdb.org/t/p/original/$posterPath"
    } else {
        null
    }

    if (imageUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(Res.drawable.app_icon),
            contentDescription = movie?.title ?: "Movie poster",
            contentScale = ContentScale.FillBounds,
            modifier = modifier,
            onError = { error ->
                android.util.Log.e(
                    "ImageLoading",
                    "Error loading image: $imageUrl",
                    error.result.throwable
                )
            },
            onSuccess = { success ->
                android.util.Log.d("ImageLoading", "Successfully loaded image: $imageUrl")
            }
        )
    } else {
        // What to show if there's no poster path? Maybe just the placeholder?
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = "No poster available",
            contentScale = ContentScale.FillBounds,
            modifier = modifier
        )
    }
}
