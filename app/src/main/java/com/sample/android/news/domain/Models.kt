package com.sample.android.news.domain

import android.os.Parcelable
import com.sample.android.news.util.smartTruncate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?
) : Parcelable {
    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
    val shortDescription: String?
        get() = description?.smartTruncate(200)
}

@Parcelize
data class Source(
    val id: String?,
    val name: String
) : Parcelable