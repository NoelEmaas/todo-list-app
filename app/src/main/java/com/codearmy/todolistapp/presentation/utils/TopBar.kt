package com.codearmy.todolistapp.presentation.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codearmy.todolistapp.R
import com.codearmy.todolistapp.ui.theme.MainBackground
import com.codearmy.todolistapp.ui.theme.interBold
import com.codearmy.todolistapp.ui.theme.interLight
import com.codearmy.todolistapp.ui.theme.interRegular

@SuppressLint("UnrememberedMutableState")
@Composable
fun TopBar(
    navigationController: NavController,
    openDrawer:() -> Unit,
    topBarTitle : String,
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(
            when (navigationController.currentDestination?.route) {
                "home_screen" -> {
                    MainBackground
                }
                else -> {
                    Color.White
                }
            }
        )
        .padding(20.dp, 20.dp, 20.dp, 10.dp)
        .height(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Image(
            modifier = Modifier.clickable(onClick = {
                openDrawer.invoke()
            }),
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = null)

        Text(
            text = topBarTitle,
            fontWeight = FontWeight.Bold,
            fontFamily = interBold,
            fontSize = 14.sp)

        Image(
            modifier = Modifier
                .clickable(onClick = {
                    if(navigationController.currentDestination?.route == "home_screen") {
                        navigationController.navigate("calendar_screen")
                    } else {
                        navigationController.navigate("home_screen")
                    }
                }),
            painter = if(topBarTitle == "HOME") painterResource(id = R.drawable.calendar_icon) else painterResource(id = R.drawable.home_icon),
            contentDescription = null)
    }
}

@Composable
fun TopBar(
    navigationController: NavController,
    topBarTitle : String,
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MainBackground)
        .padding(20.dp, 20.dp, 20.dp, 10.dp)
        .height(20.dp)) {

        Image(
            modifier = Modifier.clickable(onClick = {
                navigationController.popBackStack()
            }),
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null)

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = topBarTitle,
            fontWeight = FontWeight.Normal,
            fontFamily = interRegular,
            fontSize = 14.sp)
    }
}