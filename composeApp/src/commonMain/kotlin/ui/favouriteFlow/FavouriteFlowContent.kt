package ui.favouriteFlow

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import components.favouriteFlow.FavouriteFlowComponent
import ui.details.DetailsContent
import ui.favourites.FavouritesContent

@Composable
fun FavouriteFlowContent(
    component: FavouriteFlowComponent,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            Children(
                stack = component.childStack,
                modifier = Modifier.fillMaxSize(),
//                animation = stackAnimation(fade() + scale())
                animation = stackAnimation(slide(orientation = Orientation.Horizontal))
            ) {
                when (val instance = it.instance) {
                    is FavouriteFlowComponent.Child.Favourites -> FavouritesContent(
                        component = instance.component,
                    )
                    is FavouriteFlowComponent.Child.Details -> DetailsContent(component = instance.component)
                }
            }
        }
    }
}
