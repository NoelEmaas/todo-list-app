package com.codearmy.todolistapp.presentation.screens.calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.ui.theme.DarkGray
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarWeek(
    updateCurrMonthViewed : (String) -> Unit,
    updateCurrDateViewed : (String) -> Unit,
    dateEventCount : (String) -> Int,
    checkIfDateSelected : (String) -> Boolean
) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
    updateCurrMonthViewed(getWeekPageTitle(visibleWeek))

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
        WeekCalendar(
            state = state,
            dayContent = {
                val dateToString = "${it.date.year}-${month[it.date.month.toString()]}-${it.date.dayOfMonth}"
                Day(
                    day = it,
                    dateToString = dateToString,
                    eventCount = dateEventCount(dateToString),
                    isSelected = checkIfDateSelected(dateToString),
                    updateCurrDateViewed = updateCurrDateViewed,
                )
            }
        )
    }
}

@Composable
fun Day(
    day: WeekDay,
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
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    updateCurrDateViewed(dateToString)
                }),
        contentAlignment = Alignment.Center
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


