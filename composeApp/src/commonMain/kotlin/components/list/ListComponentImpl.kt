package components.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import models.Article
import models.ArticlesListObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


class ListComponentImpl(
    componentContext: ComponentContext,
    private val onListItemClicked: (Article) -> Unit
) : ListComponent, ComponentContext by componentContext {

    private val _uiState = MutableValue(stateKeeper.consume<ArticlesState>(key = "LIST_UI_STATE", strategy = ArticlesState.serializer()) ?: ArticlesState())
    override val uiState: Value<ArticlesState> = _uiState

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    init {
        stateKeeper.register(key = "LIST_UI_STATE", strategy = ArticlesState.serializer()) { _uiState.value }
    }

    private suspend fun getArticles(page: Int): List<Article> =
        httpClient
            .get("https://api.spaceflightnewsapi.net/v4/articles/?limit=10&offset=${page * 10}")
            .body<ArticlesListObject>().results

    override fun loadMoreArticles() {
        runBlocking {
            coroutineScope {
                val newArticles = getArticles(uiState.value.page)
                _uiState.update { it.copy(
                    articles = it.articles + newArticles,
                    page = it.page + 1
                ) }
            }
        }
    }

    override fun onItemClicked(article: Article) {
        onListItemClicked(article)
    }
}

@Serializable
data class ArticlesState(
    val articles: List<Article> = emptyList(),
    val page: Int = 0
)

