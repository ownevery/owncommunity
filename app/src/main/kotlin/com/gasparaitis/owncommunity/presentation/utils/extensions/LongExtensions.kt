package com.gasparaitis.owncommunity.presentation.utils.extensions

import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val Long.humanReadableFollowerCount: String
    get() {
        if (this == 1L) return "1 follower"
        val millions = this / 1_000_000
        val thousands = this / 1_000
        val secondDigit =
            (this / 10).run {
                val digit = (this / 10) % 10
                if (digit == 0L) {
                    ""
                } else {
                    ".$digit"
                }
            }
        return if (millions != 0L) {
            "${millions}${secondDigit}m followers"
        } else if (thousands >= 100L) {
            "${thousands}k followers"
        } else if (thousands >= 10L) {
            "${thousands}${secondDigit}k followers"
        } else {
            "$this followers"
        }
    }
val Long.humanReadableActionCount: String
    get() {
        val millions = this / 1_000_000
        val thousands = this / 1_000
        val secondDigit =
            (this / 10).run {
                val digit = (this / 10) % 10
                if (digit == 0L) {
                    ""
                } else {
                    ".$digit"
                }
            }
        return if (millions != 0L) {
            "${millions}${secondDigit}m"
        } else if (thousands >= 100L) {
            "${thousands}k"
        } else if (thousands >= 10L) {
            "${thousands}${secondDigit}k"
        } else {
            "$this"
        }
    }
val Long.humanReadableTimeAgo: String get() =
    (ClockUtils.epochMillis - this)
        .absoluteValue
        .toDuration(DurationUnit.MILLISECONDS)
        .toComponents { days: Long, hours: Int, minutes: Int, seconds: Int, _: Int ->
            if (days != 0L) {
                days.absoluteValue.toString().plus("d ago")
            } else if (hours != 0) {
                hours.absoluteValue.toString().plus("h ago")
            } else if (minutes != 0) {
                minutes.absoluteValue.toString().plus("m ago")
            } else {
                floor(seconds.toDouble()).absoluteValue.toString().plus("s ago")
            }
        }
