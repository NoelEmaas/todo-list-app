package com.codearmy.todolistapp.presentation.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.codearmy.todolistapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController : NavController) {

    var activityToLaunch =
        if(Firebase.auth.currentUser != null) "home_screen"
        else "signin_screen"

    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(activityToLaunch)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)){

        Image(painter = painterResource(id = R.drawable.splash_check_icon),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))

    }
}