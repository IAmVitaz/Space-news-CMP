package components.favourites

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import models.Article

class FavouritesComponentImpl(
    componentContext: ComponentContext,
    private val onListItemClicked: (Article) -> Unit
) : FavouritesComponent, ComponentContext by componentContext {

    private val _uiState = MutableValue(stateKeeper.consume<FavouriteArticlesState>(key = "LIST_UI_STATE", strategy = FavouriteArticlesState.serializer()) ?: FavouriteArticlesState())
    override val uiState: Value<FavouriteArticlesState> = _uiState

    init {
        stateKeeper.register(key = "LIST_UI_STATE", strategy = FavouriteArticlesState.serializer()) { _uiState.value }
    }

    override fun getFavouriteArticles() {
        runBlocking {
            coroutineScope {
                val newArticles = listOf<Article>(
                    Article(
                        id = "22508",
                        title = "Test Article",
                        url = "https://spacenews.com/japan-funding-water-based-satellite-propulsion-upgrade/",
                        imageUrl = "https://i0.wp.com/spacenews.com/wp-content/uploads/2024/02/rsz_water-hall-effect-thruster.png",
                        newsSite = "SpaceNews",
                        summary = "Japan has awarded Tokyo-based small satellite thruster developer Pale Blue a grant worth up to $27 million to upgrade its water-based propulsion technology for larger spacecraft.",
                        publishedAt = "2024-02-07T22:45:26Z"
                    )
                )
                _uiState.update {
                    it.copy(
                        articles = it.articles + newArticles,
                    )
                }
            }
        }
    }

    override fun onItemClicked(article: Article) {
        onListItemClicked(article)
    }
}

@Serializable
data class FavouriteArticlesState(
    val articles: List<Article> = emptyList(),
)
