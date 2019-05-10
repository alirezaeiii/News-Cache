package com.sample.android.news.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeParser {

    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    init {
        formatter.timeZone = TimeZone.getTimeZone("GMT")
    }

    fun toMillis(dateTime: String) = formatter.parse(dateTime).time
}