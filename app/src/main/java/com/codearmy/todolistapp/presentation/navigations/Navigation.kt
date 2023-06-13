package com.codearmy.todolistapp.presentation.navigations

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codearmy.todolistapp.presentation.screens.addedittask.AddEditTaskScreen
import com.codearmy.todolistapp.presentation.screens.addedittask.AddEditTaskViewModel
import com.codearmy.todolistapp.presentation.screens.calendar.CalendarScreen
import com.codearmy.todolistapp.presentation.screens.calendar.CalendarViewModel
import com.codearmy.todolistapp.presentation.screens.home.HomeScreen
import com.codearmy.todolistapp.presentation.screens.home.HomeViewModel
import com.codearmy.todolistapp.presentation.screens.splash.SplashScreen
import com.codearmy.todolistapp.presentation.screens.signin.SignInScreen
import com.codearmy.todolistapp.presentation.task.TaskListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    navigationController : NavHostController = rememberNavController(),
    addEditTaskViewModel: AddEditTaskViewModel,
    homeViewModel: HomeViewModel,
    calendarViewModel: CalendarViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {

    NavHost(navController = navigationController, startDestination = NavScreen.SplashScreen.route){
        composable(route = NavScreen.SplashScreen.route){
            SplashScreen(navigationController)
        }
        composable(route = NavScreen.SignInScreen.route){
            SignInScreen(navigationController)
        }
        composable(route = NavScreen.HomeScreen.route){
            val scaffoldState : ScaffoldState = rememberScaffoldState()
            HomeScreen(
                navController = navigationController,
                openDrawer = {coroutineScope.launch { scaffoldState.drawerState.open() } },
                scaffoldState = scaffoldState,
                homeViewModel = homeViewModel,
            )
        }
        composable(
            route = NavScreen.AddEditTaskScreen.route + "?taskId={taskId}&onEditTask={onEditTask}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("onEditTask") {
                    defaultValue = true
                    type = NavType.BoolType
                }
            )
        ){ backStackEntry ->

            val taskId = backStackEntry.arguments?.getString("taskId").toString()
            val onEditTask = backStackEntry.arguments?.getBoolean("onEditTask") == true

            if(taskId != "") addEditTaskViewModel.getTask(taskId)
            else addEditTaskViewModel.resetState()

            AddEditTaskScreen(
                navController = navigationController,
                addEditTaskViewModel = addEditTaskViewModel,
                taskID = taskId,
                onEditTask = mutableStateOf(onEditTask),
            )
        }

        composable(route = NavScreen.CalendarScreen.route){
            val scaffoldState : ScaffoldState = rememberScaffoldState()
            CalendarScreen(
                navController = navigationController,
                openDrawer = {coroutineScope.launch { scaffoldState.drawerState.open() } },
                scaffoldState = scaffoldState,
                calendarViewModel = calendarViewModel,
            )
        }
    }
}