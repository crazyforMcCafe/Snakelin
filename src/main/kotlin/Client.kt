import components.Gameboard
import components.Title
import kotlinx.browser.document
import kotlinx.browser.window
import org.reduxkotlin.createStore
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import react.*
import react.dom.client.createRoot
import react.dom.events.KeyboardEventHandler
import react.dom.html.ReactHTML.div
import store.AppState
import store.SettingsList
import store.reducers.Direction
import store.reducers.MoveSnakeAction
import store.rootReducer

val INITIAL_STATE = AppState(
    gameState = null,
    snake = listOf(),
    settingsList = SettingsList(0, 0)
)

object Store {
    val appStore = createStore(rootReducer, INITIAL_STATE)
}

val App = FC<Props> {
    Title()
    Gameboard()
}

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    createRoot(container).render(App.create())
}
