package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage(): String

    companion object AbstractFactory {

        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date,
            type: String,
            payload: String,
            isIncoming: Boolean = false
        ): BaseMessage {
            return when (type) {
                MessageType.IMAGE.toString() -> makeTextMessage(from, chat, date, payload, isIncoming)
                MessageType.TEXT.toString() -> makeImageMessage(from, chat, date, payload, isIncoming)
                else -> throw IllegalArgumentException("Unknown message type")
            }
        }

        private fun makeTextMessage(from: User?, chat: Chat, date: Date, text: String, isIncoming: Boolean) =
            TextMessage(UUID.randomUUID().toString(), from, chat, date, text, isIncoming)

        private fun makeImageMessage(from: User?, chat: Chat, date: Date, url: String, isIncoming: Boolean) =
            ImageMessage(UUID.randomUUID().toString(), from, chat, date, url, isIncoming)
    }
}