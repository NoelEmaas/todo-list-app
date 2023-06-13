package com.codearmy.todolistapp.presentation.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codearmy.todolistapp.ui.theme.interLight
import com.codearmy.todolistapp.ui.theme.interMedium
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek
                    .getDisplayName(TextStyle.SHORT, Locale.getDefault()).toString()
                    .subSequence(0, 3)
                    .toString()
                    .uppercase(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = interMedium
            )
        }
    }
}

fun getWeekPageTitle(week: Week): String {
    val firstDate = week.days.first().date
    val lastDate = week.days.last().date
    return when {
        firstDate.yearMonth == lastDate.yearMonth -> {
            firstDate.yearMonth.displayText()
        }
        firstDate.year == lastDate.year -> {
            "${firstDate.month.displayText(short = false)} - ${lastDate.yearMonth.displayText()}"
        }
        else -> {
            "${firstDate.yearMonth.displayText()} - ${lastDate.yearMonth.displayText()}"
        }
    }
}

@Composable
fun rememberFirstVisibleWeekAfterScroll(state: WeekCalendarState): Week {
    val visibleWeek = remember(state) { mutableStateOf(state.firstVisibleWeek) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleWeek.value = state.firstVisibleWeek }
    }
    return visibleWeek.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

@Composable
fun EventMark(evenCount : Int) {
    Row(modifier = Modifier.size(32.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .size(12.dp)
            .background(Color(0XFF6A84FF)),
            contentAlignment = Alignment.Center){
            Text("$evenCount", color = Color.White, fontSize = 8.sp)
        }
    }
}

@Composable
fun DayDecor(day : WeekDay, isSelected: Boolean) {
    val currentDate = remember { LocalDate.now() }

    val color = when {
        (day.date == currentDate && isSelected) || isSelected -> {
            Color.White
        }
        day.date == currentDate -> {
            Color.DarkGray
        }
        else -> {
            Color(0XFF909090)
        }
    }

    val fontWeight = when {
        day.date == currentDate || isSelected -> {
            FontWeight.Bold
        }
        else -> {
            FontWeight.Normal
        }
    }

    Text(text = day.date.dayOfMonth.toString(),
        color = color,
        fontWeight = fontWeight,
        fontSize = 12.sp
    )
}

@Composable
fun DayDecor(day : CalendarDay, isSelected: Boolean) {
    val currentDate = remember { LocalDate.now() }

    val color = when(day.position) {
        DayPosition.MonthDate -> {
            when {
                (day.date == currentDate && isSelected) || isSelected -> {
                    Color.White
                }
                day.date == currentDate -> {
                    Color.DarkGray
                }
                else -> {
                    Color(0XFF909090)
                }
            }
        }
        DayPosition.InDate -> Color(0XFFF0F0F0)
        DayPosition.OutDate -> Color(0XFFF0F0F0)
    }

    val fontWeight = when {
        day.date == currentDate || isSelected -> {
            FontWeight.Bold
        }
        else -> {
            FontWeight.Normal
        }
    }

    Text(text = day.date.dayOfMonth.toString(),
        color = color,
        fontWeight = fontWeight,
        fontSize = 12.sp
    )
}