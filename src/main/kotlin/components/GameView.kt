package components

import Handler
import Store
import csstype.*
import emotion.react.css
import kotlinx.js.timers.Timeout
import kotlinx.js.timers.clearInterval
import kotlinx.js.timers.setInterval
import org.w3c.dom.events.Event
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffect
import react.useState
import store.Gameboard
import store.GridSpaceRoles
import store.reducers.Direction
import store.reducers.GameState
import store.reducers.MoveSnakeAction
import store.reducers.SetGameStateAction
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

external interface GameViewProps : Props {
    var addToWindow: (type: String, Handler) -> Unit
    var size: Int
    var tempo: Int
    var gameboard: Gameboard
    var gameState: GameState
}

val GameView = FC<GameViewProps> { props ->
    val store = Store.appStore
    var lastPressedKey: String? by useState(null)
    var moveSnakeInterval: Timeout? by useState(null)

    fun getDirectionFromKey(key: String) = when (key) {
        "KeyW", "ArrowUp" -> Direction.UP
        "KeyA", "ArrowLeft" -> Direction.LEFT
        "KeyS", "ArrowDown" -> Direction.DOWN
        "KeyD", "ArrowRight" -> Direction.RIGHT
        else -> null
    }

    fun moveSnake(keyPressed: String) {
        lastPressedKey = keyPressed
        val direction = getDirectionFromKey(keyPressed)

        direction?.let { dir -> store.dispatch(MoveSnakeAction(dir)) }
    }

    fun changeDirection(keyPressed: String) {
        if (lastPressedKey != keyPressed) moveSnake(keyPressed)
    }

    fun pressEscapeHandler() {
        if (props.gameState != GameState.PAUSED) {
            store.dispatch(SetGameStateAction(GameState.PAUSED))
            moveSnakeInterval?.let { clearInterval(it) }
        } else {
            store.dispatch(SetGameStateAction(GameState.PLAYING))
            lastPressedKey?.let {
                moveSnake(it)
                moveSnakeInterval = setInterval(props.tempo.milliseconds) { moveSnake(it) }
            }
        }
    }

    val keyDownHandler: (Event) -> Unit = { e ->
        val key = e.asDynamic().code as String
        if (key == "Escape") {
            pressEscapeHandler()
        } else if (props.gameState == GameState.PLAYING) changeDirection(key)
    }

    props.addToWindow("keydown", keyDownHandler)

    useEffect(lastPressedKey) {
        moveSnakeInterval?.let { clearInterval(it) }
        moveSnakeInterval = setInterval(props.tempo.milliseconds) {
            lastPressedKey?.let { moveSnake(it) }
        }
    }

    div {
        css {
            margin = Auto.auto
            marginTop = 6.rem

            outline = Outline(((0.1).rem), LineStyle.solid, Color("gray"))
            width = 70.vh
            height = 70.vh
            display = Display.grid
            gridTemplateRows = repeat(props.size, 1.fr)
            gridTemplateColumns = repeat(props.size, 1.fr)
        }

        props.gameboard.board.flatten().map {
            div {
                css {
                    outline = Outline(((0.1).rem), LineStyle.solid, Color("#0f0f0f"))
                    backgroundColor = when (it.role) {
                        GridSpaceRoles.SNAKE_HEAD -> Color(ReusableCSS.SNAKE_HEAD_COLOR)
                        GridSpaceRoles.SNAKE_BODY -> Color(ReusableCSS.SNAKE_BODY_COLOR)
                        GridSpaceRoles.FOOD -> Color(ReusableCSS.FOOD_COLOR)
                        GridSpaceRoles.EMPTY -> Color(ReusableCSS.GRID_BACKGROUND_COLOR)
                    }
                    color = Color("green")
                }
                id = "gridSpace__${it.row}-${it.column}"
            }
        }
    }
}