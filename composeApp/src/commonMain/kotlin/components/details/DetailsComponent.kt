package components.details

import com.arkivanov.decompose.value.Value
import models.Article

interface DetailsComponent {
    val uiState: Value<ArticleState>

    fun onUpdateArticle(newArticle: Article)
    fun onBackClicked()
}