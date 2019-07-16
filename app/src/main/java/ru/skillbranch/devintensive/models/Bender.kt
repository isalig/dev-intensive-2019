package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> = when {
        !question.isAnswerValid(answer) -> "${getValidationError()}\nquestion.question}" to status.color
        question.answers.contains(answer) -> {
            question = question.nextQuestion()
            "Отлично - это правильный ответ\n${question.question}" to status.color
        }
        status == Status.CRITICAL -> {
            question = Question.NAME
            status = Status.NORMAL
            "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        }
        else -> {
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    private fun getValidationError(): String = when (question) {
        Question.NAME -> "Имя должно начинаться с заглавной буквы"
        Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
        Question.MATERIAL -> "Материал не должен содержать цифр"
        Question.BDAY -> "Год моего рождения должен содержать только цифры"
        Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
        Question.IDLE -> throw IllegalStateException("IDLE state must not be validated")
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status = if (ordinal < values().lastIndex) {
            values()[ordinal + 1]
        } else {
            values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun isAnswerValid(answer: String) = answer.firstOrNull()?.isUpperCase() == true
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun isAnswerValid(answer: String) = answer.firstOrNull()?.isLowerCase() == true
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun isAnswerValid(answer: String): Boolean {
                answer.forEach { if (it.isDigit()) return false }
                return true
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun isAnswerValid(answer: String) = answer.isDigitsOnly()
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun isAnswerValid(answer: String) = answer.isDigitsOnly() && answer.length == 7
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun isAnswerValid(answer: String) = true
        };

        abstract fun nextQuestion(): Question

        abstract fun isAnswerValid(answer: String): Boolean
    }
}