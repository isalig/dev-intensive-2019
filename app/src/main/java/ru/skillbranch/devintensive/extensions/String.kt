package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16) = with(trim()) {
    if (this.length <= length) {
        this
    } else {
        substring(0, length + 1).trim() + "..."
    }
}