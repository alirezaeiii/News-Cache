package com.sample.android.news.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sample.android.news.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import timber.log.Timber

/**
 * A retrofit service to fetch a news list.
 */
interface NewsService {
    @GET("v2/top-headlines?country=us&category=business")
    fun getNews(): Deferred<NetworkArticleContainer>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
            .build()

        val request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

fun getLoggerInterceptor(): Interceptor {
    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        Timber.d(it)
    })
    logger.level = HttpLoggingInterceptor.Level.BASIC
    return logger
}

/**
 * Main entry point for network access. Call like `Network.news.getNews()`
 */
object Network {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.NEWS_BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor())
                .addInterceptor(getLoggerInterceptor())
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val news = retrofit.create(NewsService::class.java)
}

