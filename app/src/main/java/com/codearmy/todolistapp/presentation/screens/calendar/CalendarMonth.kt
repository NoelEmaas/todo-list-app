package com.codearmy.todolistapp.presentation.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.ui.theme.DarkGray
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalenderMonth(
    updateCurrMonthViewed : (String) -> Unit,
    updateCurrDateViewed : (String) -> Unit,
    dateEventCount : (String) -> Int,
    checkIfDateSelected : (String) -> Boolean
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
    updateCurrMonthViewed(visibleMonth.yearMonth.displayText())

    val month = mapOf(
        "JANUARY" to "01",
        "FEBRUARY" to "02",
        "MARCH" to "03",
        "APRIL" to "04",
        "MAY" to "05",
        "JUNE" to "06",
        "JULY" to "07",
        "AUGUST" to "08",
        "SEPTEMBER" to "09",
        "OCTOBER" to "10",
        "NOVEMBER" to "11",
        "DECEMBER" to "12"
    )


    Column() {
        HorizontalCalendar(
            state = state,
            dayContent = {
                val dateToString = "${it.date.year}-${month[it.date.month.toString().uppercase()]}-${it.date.dayOfMonth}"
                Day(
                    day = it,
                    dateToString = dateToString,
                    eventCount = dateEventCount(dateToString),
                    isSelected = checkIfDateSelected(dateToString),
                    updateCurrDateViewed = updateCurrDateViewed
                )
            }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    dateToString: String,
    eventCount: Int,
    isSelected: Boolean,
    updateCurrDateViewed: (String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    updateCurrDateViewed(dateToString)
                },
            ),
        contentAlignment = Alignment.Center,
    ) {

        Box(modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .size(32.dp)
            .background(
                if (isSelected) DarkGray
                else Color.White
            ),
            contentAlignment = Alignment.Center
        ){
            DayDecor(day = day, isSelected)
        }

        if(eventCount > 0){
            EventMark(evenCount = eventCount)
        }
    }
}