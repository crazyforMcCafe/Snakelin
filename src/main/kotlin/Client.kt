import kotlinx.browser.document
import kotlinx.browser.window
import org.reduxkotlin.createStore
import org.w3c.dom.events.Event
import react.*
import react.dom.client.createRoot
import store.AppState
import store.rootReducer
import components.menu.Menu
import store.Gameboard
import store.reducers.InitBoardAction
import store.reducers.Settings

val INITIAL_STATE = AppState(
    gameState = null,
    settingsIndices = mapOf(Settings.TEMPO to 0, Settings.SIZE to 0),
    gameboard = Gameboard(10, 10)
)

object Store {
    val appStore: org.reduxkotlin.Store<AppState> = createStore(rootReducer, INITIAL_STATE)
}

typealias Handler = (Event) -> Unit

/**
 * Regarding this function's existence:
 * Handlers for keyboard events must be added on the window. This function is passed down to [Gameboard].kt for this purpose.
 */
object WindowHandler {
    private val handlers = mutableMapOf<String, Handler>()

    val addToWindowHandler: (type: String, Handler) -> Unit = { type, handler ->

        window.removeEventListener(type, handlers[type])

        handlers[type] = handler
        window.addEventListener(type, handler)
    }
}

val App = FC<Props> {
    val store = Store.appStore
//    var state by useState(store.state)
//
//    val unsubscribe = store.subscribe { state = store.state }

    useEffectOnce { store.dispatch(InitBoardAction(store.state.settingsValues.size)) }

    Menu()
}

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    createRoot(container).render(App.create())
}
