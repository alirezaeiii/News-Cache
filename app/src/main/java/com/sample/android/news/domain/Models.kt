package com.sample.android.news.domain

class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String?
)

class Source(
    val id: String?,
    val name: String
)