package com.codearmy.todolistapp.presentation.navigations

sealed class NavScreen(val route : String){
    object SplashScreen : NavScreen("splash_screen")
    object SignInScreen : NavScreen("signin_screen")
    object HomeScreen : NavScreen("home_screen")
    object AddEditTaskScreen : NavScreen("addedit_screen")
    object CalendarScreen : NavScreen("calendar_screen")
}
