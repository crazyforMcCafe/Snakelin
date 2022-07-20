package components

import Handler
import Store
import csstype.*
import emotion.react.css
import org.w3c.dom.events.Event
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import store.reducers.Direction
import store.reducers.Gameboard
import store.reducers.GridSpaceRoles
import store.reducers.MoveSnakeAction
import utils.ReusableCSS


external interface GameboardProps : Props {
    var addToWindow: (type: String, Handler) -> Unit
    var size: Int
    var gameboard: Gameboard
}

val Gameboard = FC<GameboardProps> {
    val store = Store.appStore

    val keyDownHandler: (Event) -> Unit = { e ->
        val key = e.asDynamic().code as String
        val direction = when (key) {
            "KeyW", "ArrowUp" -> Direction.UP
            "KeyA", "ArrowLeft" -> Direction.LEFT
            "KeyS", "ArrowDown" -> Direction.DOWN
            "KeyD", "ArrowRight" -> Direction.RIGHT
            else -> null
        }

        direction?.let { dir -> store.dispatch(MoveSnakeAction(dir)) }
    }

    it.addToWindow("keydown", keyDownHandler)

    div {
        css {
            ReusableCSS.centered(this)

            outline = Outline(((0.1).rem), LineStyle.solid, Color("gray"))
            width = 70.vh
            height = 70.vh
            display = Display.grid
            gridTemplateRows = repeat(it.size, 1.fr)
            gridTemplateColumns = repeat(it.size, 1.fr)
        }

        it.gameboard.board.map {
            div {
                css {
                    outline = Outline(((0.1).rem), LineStyle.solid, Color("#0f0f0f"))
                    backgroundColor = when (it.role) {
                        GridSpaceRoles.SNAKE_HEAD -> Color(ReusableCSS.SNAKE_HEAD_COLOR)
                        GridSpaceRoles.SNAKE_BODY -> Color(ReusableCSS.SNAKE_BODY_COLOR)
                        GridSpaceRoles.FOOD -> Color(ReusableCSS.FOOD_COLOR)
                        GridSpaceRoles.EMPTY -> Color(ReusableCSS.GRID_BACKGROUND_COLOR)
                    }
                }
                id = "gridSpace__${it.row}-${it.column}"
            }
        }
    }
}