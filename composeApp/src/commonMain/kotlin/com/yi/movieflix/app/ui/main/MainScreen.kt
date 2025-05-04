package com.yi.movieflix.app.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.movieflix.app.data.remote.MovieClient
import com.movieflix.app.models.playing.NowPlaying
import com.movieflix.app.models.playing.NowPlayingResult
import io.ktor.client.call.body
import kotlinx.coroutines.launch
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
fun MainScreen(navController: NavController?, client: MovieClient?) {
    val scope = rememberCoroutineScope()
    val movieList = remember { mutableStateOf<List<NowPlayingResult>>(emptyList()) }

    // Fetching movies in a coroutine
    LaunchedEffect(Unit) {
        scope.launch {
            val result = client?.getMovies()
            val res = result?.body() as NowPlaying
            movieList.value = res.results  // Set the movie list to be displayed
            android.util.Log.d("RESPONSE", "MainScreen: ${res}")
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
            items(movieList.value) { movie ->

                android.util.Log.d("RESPONSE2", "MainScreen: ${movie.title}")
                MovieItem(
                    movie,
                    Modifier
                        .size(150.dp, 200.dp)
                        /*  .padding(all = 3.dp)*/
                        .background(color = Color.White, RoundedCornerShape(15.dp))
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
            items(10) {
                MovieItem(
                    null,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
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
            items(10) {
                MovieItem(
                    null,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
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
            items(10) {
                MovieItem(
                    null,
                    Modifier
                        .size(150.dp, 200.dp)
                        .background(color = Color.White, RoundedCornerShape(15.dp))
                )
            }
        }

    }
}

@Composable
fun MovieItem(movie: NowPlayingResult?, modifier: Modifier) {
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
//                    painterResource(Res.drawable.app_icon), contentDescription = "",
                loadingImage(movie, Modifier.fillMaxWidth().weight(1f))
                /*  AsyncImage(
                      model = ImageRequest.Builder(LocalPlatformContext.current)
                          .data(
                              "https://image.tmdb.org/t/p/original/${
                                  movie?.posterPath?.substringAfter(
                                      "/"
                                  )
                              }"
                          )
                          .crossfade(true)
                          .build(),
                      placeholder = painterResource(Res.drawable.app_icon),
                      contentDescription = "",
                      contentScale = ContentScale.FillBounds,
                      modifier = Modifier.fillMaxWidth().weight(1f),
                  )*/

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
fun loadingImage(movie: NowPlayingResult?, modifier: Modifier) {
    val posterPath = movie?.posterPath
    android.util.Log.d(
        "ImageLoading",
        "Movie: ${movie != null}, Poster Path: $posterPath"
    )

    val imageUrl = if (posterPath?.startsWith("/") == true) {
        "https://image.tmdb.org/t/p/original${posterPath}"
    } else if (posterPath != null) {
        "https://image.tmdb.org/t/p/original/$posterPath"
    } else {
        null // Handle null posterPath explicitly
    }

    android.util.Log.d("ImageLoading", "Final Image URL: $imageUrl") // Log the final URL

    if (imageUrl != null) { // Only load if URL is not null
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(imageUrl) // Use the constructed URL
                .crossfade(true)
                // Add error handling (see step 3)
                .build(),
            placeholder = painterResource(Res.drawable.app_icon),
            contentDescription = movie?.title ?: "Movie poster",
            contentScale = ContentScale.FillBounds,
            modifier = modifier,
            // Add error handling (see step 3)
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
            contentScale = ContentScale.FillBounds, // Or another appropriate scale
            modifier = modifier
        )
    }
}
