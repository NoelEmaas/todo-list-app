package com.codearmy.todolistapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codearmy.todolistapp.presentation.home.components.UserName
import com.codearmy.todolistapp.presentation.home.components.UserPicture
import com.codearmy.todolistapp.presentation.navigations.NavScreen
import com.codearmy.todolistapp.presentation.utils.SideDrawer
import com.codearmy.todolistapp.presentation.utils.TopBar
import com.codearmy.todolistapp.presentation.task.TaskList
import com.codearmy.todolistapp.presentation.task.TaskListViewModel
import com.codearmy.todolistapp.presentation.utils.NoTaskDisplay
import com.codearmy.todolistapp.ui.theme.DarkGray
import com.codearmy.todolistapp.ui.theme.MainBackground
import com.codearmy.todolistapp.ui.theme.interBold
import com.codearmy.todolistapp.ui.theme.interLight
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    openDrawer:() -> Unit,
    scaffoldState: ScaffoldState,
    homeViewModel: HomeViewModel,
    taskListViewModel: TaskListViewModel = viewModel(modelClass = TaskListViewModel::class.java)
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopBar(navigationController = navController, openDrawer = openDrawer, topBarTitle = "HOME") },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = DarkGray,
                onClick = {
                    navController.navigate(NavScreen.AddEditTaskScreen.route)
                }) {
                Icon(Icons.Filled.Add, null, tint = Color.White)
            }
        },
        backgroundColor = MainBackground,
        drawerContent = { SideDrawer(navController = navController) }
    ) {

        val homeUiState = homeViewModel.homeUiState
        val state = rememberCollapsingToolbarScaffoldState()

        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                val size =  (state.toolbarState.progress).dp

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBackground)
                        .parallax(0.6f)
                        .graphicsLayer {
                            alpha = state.toolbarState.progress
                        }
                        .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(PaddingValues(top = 20.dp, bottom = 20.dp)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {

                        Column() {
                            UserName()
                            Spacer(modifier = Modifier.height(10.dp))
                            CurrentNumberOfTasks()
                        }

                        UserPicture(70)
                    }

                    Column(
                        modifier = Modifier
                            .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(Color.White),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        if (homeUiState.hasNetworkAccess && homeUiState.hasLocationAccess) {
                            UserLocation(homeViewModel.getLocation())

                            WeatherInfo(
                                temperature = homeViewModel.getTemperature(),
                                humidity = homeViewModel.getHumidity(),
                                precipitation = homeViewModel.getPrecipitation(),
                                weatherType = homeViewModel.getTypeOfWeather(),
                                weatherIcon = homeViewModel.getWeatherIcon()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    !homeUiState.hasNetworkAccess && !homeUiState.hasLocationAccess -> NoAccessDisplay(
                                        textToDisplay = "No internet connection and\nlocation is not enabled"
                                    )
                                    !homeUiState.hasNetworkAccess -> NoAccessDisplay(
                                        textToDisplay = "No internet connection"
                                    )
                                    !homeUiState.hasLocationAccess -> NoAccessDisplay(
                                        textToDisplay = "Location is not enabled"
                                    )
                                }

                            }

                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .road(Alignment.CenterStart, Alignment.BottomEnd)
                        .padding(60.dp, 16.dp, 16.dp, 16.dp)
                        .size(size),
                )
            }
        ){

            Box(modifier = Modifier
                .fillMaxSize()){

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp))
                ) {

                    TaskTabButtons(
                        onActiveButtonChange = homeViewModel::onActiveButtonChange,
                        homeUiState = homeUiState
                    )

                    if (homeUiState.activeButton == "todo") {
                        if (taskListViewModel.getUnfinishedTasks().isNotEmpty())
                            TaskList(
                                displayFinishedTask = false,
                                navController = navController,
                            )
                        else
                            NoTaskDisplay("No Unfinished Tasks")
                    } else {
                        if (taskListViewModel.getFinishedTasks().isNotEmpty())
                            TaskList(
                                displayFinishedTask = true,
                                navController = navController,
                            )
                        else
                            NoTaskDisplay("No finished Tasks")
                    }

                }

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MainBackground
                                )
                            )
                        )
                        .align(Alignment.BottomCenter)
                )

            }

        }
    }
}

@Composable
fun WeatherInfo(
    temperature : Double?,
    humidity: Double?,
    precipitation: Double?,
    weatherType: String,
    weatherIcon: Int
) {
    Row(modifier = Modifier
        .width(280.dp)
        .height(80.dp)
        .padding(20.dp, 0.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Column(modifier = Modifier.height(70.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            InfoWithIcon(info = "  Temperature: $temperature °C", icon = painterResource(id = com.codearmy.todolistapp.R.drawable.thermo_icon))
            InfoWithIcon(info = "  Humidity: $humidity %", icon = painterResource(id = com.codearmy.todolistapp.R.drawable.humidity_icon))
            InfoWithIcon(info = "  Precipitation: $precipitation mm", icon = painterResource(id = com.codearmy.todolistapp.R.drawable.precipitation_icon))
        }

        Column(
            modifier = Modifier.padding(PaddingValues(start = 10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                modifier = Modifier.scale(1f),
                painter = painterResource(id = weatherIcon),
                contentDescription = null
            )

            Text(text = weatherType,
                fontWeight = FontWeight.Light,
                fontFamily = interLight,
                fontSize = 12.sp)
        }
    }
}

@Composable
fun UserLocation(
    userLocation : String
) {
    Row(modifier = Modifier
        .padding(20.dp, 10.dp, 0.dp, 0.dp)
        .width(250.dp)) {
        Text(
            text = "Weather today at $userLocation:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            fontFamily = interLight
        )
    }
}

@Composable
fun NoAccessDisplay(textToDisplay: String) {
    Text(
        text = textToDisplay,
        textAlign = TextAlign.Center,
        fontFamily = interLight,
        fontSize = 14.sp,
        color = Color(0XFFC1C1C1)
    )
}

@Composable
fun TaskTabButtons(
    onActiveButtonChange: (String) -> Unit,
    homeUiState: HomeUiState
) {

    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(PaddingValues(start = 10.dp, end = 10.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier
            .height(45.dp)
            .width(150.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onActiveButtonChange("todo")
                }),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "TO DO",
                fontFamily = interBold,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (homeUiState.activeButton == "todo") Color.Black else Color(0XFFC6C6C6)
            )
        }

        Divider(modifier = Modifier
            .height(20.dp)
            .width(1.dp)
            .background(Color(0XFFABABAB)))

        Box(modifier = Modifier
            .height(45.dp)
            .width(150.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onActiveButtonChange("done")
                }),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "DONE",
                fontFamily = interBold,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (homeUiState.activeButton == "done") Color.Black else Color(0XFFC6C6C6)
            )
        }
    }

    Divider(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(Color(0XFFABABAB)))
}

@Composable
fun CurrentNumberOfTasks(
    taskListViewModel: TaskListViewModel = viewModel(modelClass = TaskListViewModel::class.java)
){
    Row(modifier = Modifier.width(150.dp)) {
        Text(
            text =
            if(taskListViewModel.getUnfinishedTasks().isNotEmpty()) {
                "You currently have ${ taskListViewModel.getUnfinishedTasks().size } unfinished tasks"
            }
            else{
                "Yay!, You currently don't have any task"
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            fontFamily = interLight
        )
    }
}

@Composable
fun InfoWithIcon(info : String, icon : Painter){
    Row(
        modifier = Modifier.height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(14.dp),
            painter = icon,
            contentDescription = null
        )
        Text(
            text = info,
            fontWeight = FontWeight.Light,
            fontFamily = interLight,
            fontSize = 12.sp
        )
    }
}