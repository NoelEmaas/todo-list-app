package com.codearmy.todolistapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

// import fonts
val archivoRegular = FontFamily(Font(R.font.archivo_regular))
val archivoLight = FontFamily(Font(R.font.archivo_light))
val archivoMedium = FontFamily(Font(R.font.archivo_medium))
val archivoBold = FontFamily(Font(R.font.archivo_bold))

val interRegular = FontFamily(Font(R.font.inter_regular))
val interLight = FontFamily(Font(R.font.inter_light))
val interBold = FontFamily(Font(R.font.inter_bold))
val interBlack = FontFamily(Font(R.font.inter_black))
val interMedium = FontFamily(Font(R.font.inter_medium))