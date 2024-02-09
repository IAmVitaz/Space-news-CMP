package components.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlinx.serialization.Serializable
import models.Article

class DetailsComponentImpl(
    componentContext: ComponentContext,
    article: Article?,
    private val onBackButtonClicked: () -> Unit
): DetailsComponent, ComponentContext by componentContext {
    private val _uiState = MutableValue(stateKeeper.consume<ArticleState>(key = "DETAILS_UI_STATE", strategy = ArticleState.serializer()) ?: ArticleState(article = article))
    override val uiState: Value<ArticleState> = _uiState

    init {
        stateKeeper.register(key = "DETAILS_UI_STATE", strategy = ArticleState.serializer()) { _uiState.value }
    }

    override fun onUpdateArticle(newArticle: Article) {
        _uiState.update { it.copy(article = newArticle) }
    }

    override fun onBackClicked() {
        onBackButtonClicked()
    }
}

@Serializable
data class ArticleState(
    val article: Article? = null
)
