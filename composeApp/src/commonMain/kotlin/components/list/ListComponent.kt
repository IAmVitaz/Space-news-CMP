package components.list

import com.arkivanov.decompose.value.Value
import models.Article

interface ListComponent {
    val uiState: Value<ArticlesState>

    fun loadMoreArticles()

    fun onItemClicked(article: Article)
}

