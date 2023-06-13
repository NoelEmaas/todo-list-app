package com.codearmy.todolistapp.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.ui.theme.interLight

@Composable
fun NoTaskDisplay(msg : String) {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
            Text(
                text = msg,
                Modifier.alpha(0.6f),
                fontFamily = interLight,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )

    }

}