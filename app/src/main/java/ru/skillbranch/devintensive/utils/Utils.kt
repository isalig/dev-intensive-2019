package ru.skillbranch.devintensive.utils

import ru.skillbranch.devintensive.utils.TransliterationMap.dictionary

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank()) {
            return Pair(null, null)
        }

        val nameParts = fullName.split(" ")
        return Pair(nameParts.getOrNull(0), nameParts.getOrNull(1))
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val builder = StringBuilder()

        fun getInitial(name: String?): String? {
            if (name.isNullOrBlank()) {
                return null
            }

            return name.first().toUpperCase().toString()
        }

        fun addNamePart(namePart: String?) {
            getInitial(namePart?.trim())?.let { builder.append(it) }
        }

        addNamePart(firstName)
        addNamePart(lastName)

        return if (builder.toString().isEmpty()) null else builder.toString()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        if (payload.isBlank()) {
            return ""
        }

        fun transliterateChar(char: Char): String {
            if (!dictionary.containsKey(char.toLowerCase())) {
                return char.toString()
            }

            val result = dictionary[char.toLowerCase()]?.toString() ?: ""

            if (char.isUpperCase()) {
                return result.capitalize()
            }

            return result
        }

        fun transliterate(word: String): String {
            if (word.isBlank()) {
                return ""
            }

            return word.map { transliterateChar(it) }.joinToString("")
        }


        val builder = StringBuilder()
        val payloadWords = payload.trim().split(" ")
        payloadWords.forEachIndexed { index, word ->
            builder.append(transliterate(word))
            if (index + 1 < payloadWords.size) {
                builder.append(divider)
            }
        }

        return builder.toString()
    }

    fun pluralTimeUnit(count: Int, one: String, few: String, many: String): String {
        if (count == 1) {
            return one
        }

        if (count in 2..4) {
            return few
        }

        if (count < 20) {
            return many
        }

        return when (count % 10) {
            0, in 5..9 -> many
            1 -> one
            else -> few
        }
    }
}