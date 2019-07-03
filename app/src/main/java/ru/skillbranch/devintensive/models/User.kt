package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(id, firstName, lastName, null)

    companion object Factory {

        fun makeUser(fullName: String?): User {
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(UUID.randomUUID().toString(), firstName, lastName)
        }
    }

    class Builder {

        private var id: String? = null
        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date = Date()
        private var isOnline: Boolean = false

        fun id(id: String) {
            this.id = id;
        }

        fun firstName(firstName: String?) {
            this.firstName = firstName
        }

        fun lastName(lastName: String?) {
            this.lastName = lastName
        }

        fun avatar(avatar: String?) {
            this.avatar = avatar
        }

        fun rating(rating: Int) {
            this.rating = rating
        }

        fun respect(respect: Int) {
            this.respect = respect
        }

        fun lastVisit(lastVisit: Date) {
            this.lastVisit = lastVisit
        }

        fun isOnline(isOnline: Boolean) {
            this.isOnline = isOnline
        }

        fun build(): User = id?.let {
            User(it, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
        } ?: throw IllegalStateException("id have to be initialized")
    }
}