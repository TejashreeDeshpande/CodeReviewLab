package com.tejashree.codereviewlab.features.newsfeed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "news_articles")
data class NewsArticle(
    @PrimaryKey
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("content")
    val content: String,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("author")
    val author: String?,
    val lastCachedTimestamp: Long = System.currentTimeMillis()
)

sealed interface DataState<out T> {
    data object Loading: DataState<Nothing>
    data class Success<out T>(
        val data: T,
        val isStale: Boolean = false
    ) : DataState<T>
    data class Error(val throwable: Throwable): DataState<Nothing>
}

// Stubs for missing dependencies
interface NewsApi {
    suspend fun fetchLatestNews(): List<NewsArticle>
}

interface NewsDao {
    suspend fun getCachedArticles(): List<NewsArticle>
    suspend fun clearAndInsert(articles: List<NewsArticle>)
}

class NewsRepository(
    private val api: NewsApi,
    private val dao: NewsDao
) {
    fun getNewsFeed(): Flow<DataState<List<NewsArticle>>> = flow {
        emit(DataState.Loading)

        val cachedData = dao.getCachedArticles()
        if (cachedData.isNotEmpty()) {
            emit(DataState.Success(data = cachedData, isStale = true))
        }
        try {
            val remoteData = api.fetchLatestNews()
            dao.clearAndInsert(remoteData)
            emit(DataState.Success(data = remoteData, isStale = false))
        } catch (e: Exception) {
            if (cachedData.isEmpty()) {
                emit(DataState.Error(e))
            }
        }
    }
}


