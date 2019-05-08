package com.sample.android.news.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sample.android.news.database.NewsDatabase
import com.sample.android.news.database.asDomainModel
import com.sample.android.news.domain.Article
import com.sample.android.news.network.Network
import com.sample.android.news.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticlesRepository(private val database : NewsDatabase) {

    /**
     * A list of articles that can be shown on the screen.
     */
    val articles: LiveData<List<Article>> =
        Transformations.map(database.newsDao.getArticles()) {
            it.asDomainModel()
        }

    /**
     * Refresh the articles stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the articles for use, observe [articles]
     */
    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            val news = Network.news.getNews().await()
            database.newsDao.insertAll(*news.asDatabaseModel())
        }
    }

}