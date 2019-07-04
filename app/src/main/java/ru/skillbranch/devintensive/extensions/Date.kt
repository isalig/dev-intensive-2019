package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

const val PER_SECOND = 1000L
const val PER_MINUTE = 60 * PER_SECOND
const val PER_HOUR = 60 * PER_MINUTE
const val PER_DAY = 24 * PER_HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") = SimpleDateFormat(pattern, Locale("ru"))
    .format(this)

fun Date.add(value: Int, timeUnits: TimeUnits = TimeUnits.SECOND) = timeUnits.modifyDate(this, value)

enum class TimeUnits(private val factor: Long) {

    SECOND(PER_SECOND) {
        override fun plural(value: Int) = "$value ${pluralSeconds(value)}"
    },
    MINUTE(PER_MINUTE) {
        override fun plural(value: Int) = "$value ${pluralMinutes(value)}"
    },
    HOUR(PER_HOUR) {
        override fun plural(value: Int) = "$value ${pluralHours(value)}"
    },
    DAY(PER_DAY) {
        override fun plural(value: Int) = "$value ${pluralDays(value)}"
    };

    fun modifyDate(date: Date, value: Int): Date {
        return Date(date.time + value * factor)
    }

    abstract fun plural(value: Int): String
}

fun Date.humanizeDiff(): String {
    val currentSeconds = Date().time / 1000
    val dateSeconds = time / 1000

    val diff = Math.abs(currentSeconds - dateSeconds)
    val diffPart = findHumanizedDiff(diff)

    if (diff < 1) {
        return "только что"
    }

    if (diff > 360 * 24 * 60 * 60) {
        return if (currentSeconds > dateSeconds) {
            "более года назад"
        } else {
            "более чем через год"
        }
    }

    return if (currentSeconds > dateSeconds) {
        "$diffPart назад"
    } else {
        "через $diffPart"
    }
}

private fun findHumanizedDiff(diff: Long): String = when {
    diff < 1L -> "только что"
    diff < 45L -> "несколько секунд"
    diff < 75L -> "минуту"
    diff < 45 * 60 -> {
        val minutes = diff / 60
        "$minutes ${pluralMinutes(minutes.toInt())}"
    }
    diff < 75 * 60 -> "час"
    diff < 22 * 60 * 60 -> {
        val hours = diff / 60 / 60
        "$hours ${pluralHours(hours.toInt())}"
    }
    diff < 26 * 60 * 60 -> "день"
    diff < 360 * 24 * 60 * 60 -> {
        val days = diff / 24 / 60 / 60
        "$days ${pluralDays(days.toInt())}"
    }
    else -> ""
}

private fun pluralSeconds(seconds: Int) = Utils.pluralTimeUnit(seconds, "секунду", "секунды", "секунд")

private fun pluralMinutes(minutes: Int) = Utils.pluralTimeUnit(minutes, "минуту", "минуты", "минут")

private fun pluralHours(hours: Int) = Utils.pluralTimeUnit(hours, "час", "часа", "часов")

private fun pluralDays(days: Int) = Utils.pluralTimeUnit(days, "день", "дня", "дней")