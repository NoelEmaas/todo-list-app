package com.codearmy.todolistapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.presentation.userinfo.UserViewModel
import com.codearmy.todolistapp.ui.theme.DarkGray
import com.codearmy.todolistapp.ui.theme.interBold
import com.codearmy.todolistapp.ui.theme.interLight
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun UserName(viewModel : UserViewModel = viewModel()){

    Text(text = buildAnnotatedString {
        append("Hello, ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = interBold,
                fontSize = 26.sp
            )
        ) {
            append("\n${viewModel.userName}!")
        }
    },
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        fontFamily = interLight,
        color = DarkGray,
    )
}