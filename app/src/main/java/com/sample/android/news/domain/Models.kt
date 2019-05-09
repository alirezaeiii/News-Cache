package com.sample.android.news.domain

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)