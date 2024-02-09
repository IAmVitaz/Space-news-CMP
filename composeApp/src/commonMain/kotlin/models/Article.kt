package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String,
    val title: String,
    val url: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("news_site")
    val newsSite: String,

    val summary: String,
    @SerialName("published_at")
    val publishedAt: String,
)

@Serializable
data class ArticlesListObject(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Article>
)
