package ru.skillbranch.devintensive.models

import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    date: Date,
    val text: String,
    isIncoming: Boolean
) : BaseMessage(
    id,
    from,
    chat,
    isIncoming,
    date
) {

    override fun formatMessage(): String =
        "${from?.firstName} ${if(isIncoming) "получил" else "отправил"} сообщение \"$text\" только что"
}