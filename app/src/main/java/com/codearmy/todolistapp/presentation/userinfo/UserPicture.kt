package com.codearmy.todolistapp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codearmy.todolistapp.presentation.userinfo.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codearmy.todolistapp.R

@Composable
fun UserPicture(size : Int,viewModel : UserViewModel = viewModel()) {

    Image(
        painter = rememberAsyncImagePainter(viewModel.userPicture),
        contentDescription = "",
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(100.dp), true)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(100.dp))
            .clip(RoundedCornerShape(100.dp))
            .background(Color.White)
            .size(size.dp)
            .clickable(interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {}),
        contentScale = ContentScale.Crop,
    )
}
