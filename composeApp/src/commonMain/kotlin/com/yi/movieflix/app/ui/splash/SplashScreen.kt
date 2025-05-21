package com.yi.movieflix.app.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.movieflix.app.navigation.Routes
import kotlinx.coroutines.delay
import movieflix_kmp.composeapp.generated.resources.Res
import movieflix_kmp.composeapp.generated.resources.app_icon
import movieflix_kmp.composeapp.generated.resources.tag_line
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun Splash(navController: NavHostController?) {

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xfefefe)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.app_icon), "",
                modifier = Modifier.size(width = Dp(200f), height = Dp(200f)).clip(
                    RoundedCornerShape(Dp(20f))
                ),

                )
            Spacer(modifier = Modifier.padding(top = Dp(15f)))
            Text(
                text = stringResource(Res.string.tag_line),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(value = 15f, type = TextUnitType.Sp),
                color = Color.Black

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 4.dp,
                modifier = Modifier.size(35.dp)
            )
            Spacer(Modifier.height(10.dp).fillMaxWidth())
            Text(
                text = "Developed by Yazdan Ilyas",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(value = 15f, type = TextUnitType.Sp),
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 5.dp)

            )
            LaunchedEffect(Unit) {
                delay(1000)
                navController?.navigate(Routes.MAIN_SCREEN.name)
            }
        }


    }
}
