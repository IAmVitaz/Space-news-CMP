package ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

import components.root.RootComponent
import ui.favouriteFlow.FavouriteFlowContent
import ui.favourites.FavouritesContent
import ui.homeFlow.HomeContent

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    MaterialTheme {
        Surface(modifier = modifier, color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)),
            ) {
                Children(component = component, modifier = Modifier.weight(1F))
                BottomBar(component = component, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun Children(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,

        // Workaround for https://issuetracker.google.com/issues/270656235
        animation = stackAnimation(fade()),
//            animation = tabAnimation(),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HomeFlowChild -> HomeContent(component = child.component, modifier = Modifier.fillMaxSize())
            is RootComponent.Child.FavouritesFlowChild -> FavouriteFlowContent(component = child.component, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun BottomBar(component: RootComponent, modifier: Modifier = Modifier) {
    val childStack by component.stack.subscribeAsState()
    val activeComponent = childStack.active.instance

    BottomNavigation(modifier = modifier) {
        BottomNavigationItem(
            selected = activeComponent is RootComponent.Child.HomeFlowChild,
            onClick = component::onHomeTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                )
            },
        )

        BottomNavigationItem(
            selected = activeComponent is RootComponent.Child.FavouritesFlowChild,
            onClick = component::onFavouritesTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourites",
                )
            },
        )
    }
}
