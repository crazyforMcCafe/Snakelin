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
import store.reducers.MoveSnakeAction
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

external interface GameboardProps : Props {
    var addToWindow: (type: String, Handler) -> Unit
    var size: Int
    var tempo: Int
    var gameboard: Gameboard
}

val GameView = FC<GameboardProps> { props ->
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
        if(lastPressedKey != keyPressed) {
            lastPressedKey = keyPressed
            val direction = getDirectionFromKey(keyPressed)

            direction?.let { dir -> store.dispatch(MoveSnakeAction(dir)) }
        }
    }

    val keyDownHandler: (Event) -> Unit = { e ->
        val key = e.asDynamic().code as String
        moveSnake(key)
    }

    props.addToWindow("keydown", keyDownHandler)

    console.log(moveSnakeInterval)

    useEffect(lastPressedKey) {
        moveSnakeInterval?.let { clearInterval(it) }
        moveSnakeInterval = setInterval(props.tempo.milliseconds) {
            // FIX
            lastPressedKey?.let { moveSnake(it) }
        }
    }

    div {
        css {
//            ReusableCSS.centeredHorizontal(this)
            ReusableCSS.centeredVertical(this)

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