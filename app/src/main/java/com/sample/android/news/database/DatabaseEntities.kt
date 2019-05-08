package com.sample.android.news.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.android.news.domain.Article
import com.sample.android.news.domain.Source

@Entity
class DatabaseArticle(
    @PrimaryKey
    val url: String,
    @Embedded
    val source: DatabaseSource,
    val author: String?,
    val title: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String?
)

class DatabaseSource(
    val id: String?,
    val name: String
)

fun List<DatabaseArticle>.asDomainModel(): List<Article> {
    return map {
        Article(
            url = it.url,
            source = Source(
                id = it.source.id,
                name = it.source.name
            ),
            author = it.author,
            title = it.title,
            imageUrl = it.imageUrl,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }
}