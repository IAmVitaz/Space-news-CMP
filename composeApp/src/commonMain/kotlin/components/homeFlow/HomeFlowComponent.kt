package components.homeFlow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import components.details.DetailsComponent
import components.list.ListComponent

interface HomeFlowComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class List(val component: ListComponent) : Child()
        class Details(val component: DetailsComponent) : Child()
    }
}
