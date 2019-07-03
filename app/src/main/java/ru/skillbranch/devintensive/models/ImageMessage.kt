package ru.skillbranch.devintensive.models

import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    date: Date,
    val url: String,
    isIncoming: Boolean
) : BaseMessage(
    id,
    from,
    chat,
    isIncoming,
    date
) {

    override fun formatMessage(): String =
        "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение \"$url\" только что"
}