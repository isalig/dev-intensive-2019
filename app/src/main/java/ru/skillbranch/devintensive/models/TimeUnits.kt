package ru.skillbranch.devintensive.models

import java.util.*

enum class TimeUnits(private val factor: Int) {

    SECOND(1),
    MINUTE(SECOND.factor * 60),
    HOUR(MINUTE.factor * 60),
    DAY(HOUR.factor * 24);

    fun modifyDate(date: Date, value: Int) : Date {
        return  Date((date.time / 1000 + value * factor) * 1000)
    }
}