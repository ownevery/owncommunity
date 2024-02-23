package com.gasparaiciukas.owncommunity.presentation.utils.extensions

import kotlin.time.Duration
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object ClockUtils {
    val epochMillis get() = Clock.System.now().toEpochMilliseconds()
    val dateTime get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    fun shiftCurrentTime(duration: Duration): Long {
        val instant = Clock.System.now()
        return when {
            duration.isPositive() -> instant.minus(duration).toEpochMilliseconds()
            duration.isNegative() -> instant.plus(duration).toEpochMilliseconds()
            else -> instant.toEpochMilliseconds()
        }
    }
}
