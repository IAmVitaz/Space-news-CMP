package components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

import com.arkivanov.decompose.value.Value
import components.details.DetailsComponent
import components.details.DetailsComponentImpl
import components.list.ListComponent
import components.list.ListComponentImpl
import kotlinx.serialization.Serializable
import models.Article

class RootComponentImpl(
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.List -> RootComponent.Child.List(listComponent(childComponentContext))
            is Config.Details -> RootComponent.Child.Details(detailsComponent(config, childComponentContext))
        }

    private fun listComponent(
        componentContext: ComponentContext
    ): ListComponent =
        ListComponentImpl(
            componentContext = componentContext,
            onListItemClicked = {article ->
                navigation.push(Config.Details(article = article))
            },
        )

    private fun detailsComponent(
        config: Config.Details,
        componentContext: ComponentContext
    ): DetailsComponent =
        DetailsComponentImpl(
            componentContext = componentContext,
            article = config.article,
            onBackButtonClicked = navigation::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val article: Article) : Config
    }

}