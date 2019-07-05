package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16) = with(trim()) {
    if (this.length <= length) {
        this
    } else {
        substring(0, length).trim() + "..."
    }
}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&\\d+;"), "").replace(Regex("\\s+"), " ")