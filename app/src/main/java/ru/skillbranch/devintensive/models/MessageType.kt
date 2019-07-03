package ru.skillbranch.devintensive.models

enum class MessageType {

    TEXT {
        override fun toString() = "text"
    },

    IMAGE {
        override fun toString() = "image"
    };
}