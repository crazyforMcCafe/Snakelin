import components.GameView
import components.mainMenu.MainMenu
import kotlinx.browser.document
import kotlinx.browser.window
import org.reduxkotlin.createStore
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import react.*
import react.dom.client.createRoot
import react.dom.events.MouseEventHandler
import store.AppState
import store.rootReducer
import store.Gameboard
import store.reducers.GameState
import store.reducers.InitBoardAction
import store.reducers.SetGameStateAction
import store.reducers.Settings

val INITIAL_STATE = AppState(
    gameState = GameState.NONE,
    settingsIndices = mapOf(Settings.TEMPO to 2, Settings.SIZE to 2),
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

    val addToWindow: (id: String, type: String, Handler) -> Unit = { id, type, handler ->

        window.removeEventListener(type, handlers[id])

        handlers[id] = handler
        window.addEventListener(type, handler)
    }

    val removeFromWindow: (id: String, type: String) -> Unit = { id, type ->
        window.removeEventListener(type, handlers[id])
        handlers.remove(id)
    }
}

val App = FC<Props> {
    val store = Store.appStore
    var gameState by useState(store.state.gameState)

    useEffectOnce { store.dispatch(InitBoardAction(store.state.settingsValues[Settings.SIZE]!!)) }
    store.subscribe { gameState = store.state.gameState }

    val playButtonClickHandler: MouseEventHandler<HTMLButtonElement> = {
        store.dispatch(SetGameStateAction(GameState.PLAYING))
    }

    when (gameState) {
        GameState.NONE -> MainMenu {
            onPlayButtonClick = playButtonClickHandler
        }
        GameState.PLAYING, GameState.PAUSED, GameState.OVER -> GameView()
    }
}

fun main() {
    val container = document.getElementById("root") ?: error("Could not find root container!")
    createRoot(container).render(App.create())
}
