package com.codearmy.todolistapp.presentation.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.codearmy.todolistapp.data.TaskDataSource
import com.codearmy.todolistapp.domain.model.Task
import com.himanshoe.kalendar.common.data.KalendarEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CalendarUiState (
    val currDateViewed : String = "",
    val currMonthViewed : String = "",
    val label : String = "Task/s due today"
)

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {

    var calendarUiState by mutableStateOf(CalendarUiState())

    private val currentDate: LocalDateTime = LocalDateTime.now()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val formattedDate: String = currentDate.format(formatter)
    private val currDateStrFormat = formattedDate

    init {
        updateCurrentDateViewed(currDateStrFormat)
        updateCurrentMonthViewed("${currentDate.month} ${currentDate.year}")
    }

    fun updateCurrentDateViewed(date : String){
        calendarUiState = calendarUiState.copy(currDateViewed = date)
        calendarUiState = calendarUiState.copy(label =
            if(calendarUiState.currDateViewed == currDateStrFormat) "Task/s due today"
            else "Task/s due on ${calendarUiState.currDateViewed}"
        )
    }

    fun updateCurrentMonthViewed(month : String) {
        calendarUiState = calendarUiState.copy(currMonthViewed = month)
    }

    fun isDateSelected(date : String) : Boolean {
        return date == calendarUiState.currDateViewed
    }

    fun resetToCurrentDate(){
        calendarUiState = calendarUiState.copy(currDateViewed = currDateStrFormat)
        calendarUiState = calendarUiState.copy(label ="Task/s due today")
    }

}
