package components

import Store
import WindowHandler
import components.gameMenu.GameOverMenu
import components.gameMenu.PauseMenu
import csstype.*
import emotion.react.css
import kotlinx.js.timers.Timeout
import kotlinx.js.timers.clearInterval
import kotlinx.js.timers.setInterval
import org.w3c.dom.events.Event
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section
import store.GridSpaceRoles
import store.reducers.*
import store.reducers.Direction
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

val GameView = FC<Props> {
    val store = Store.appStore
    var state by useState(store.state)

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
        val isDifferentDirection = lastPressedKey != keyPressed

        val lastDirection = getDirectionFromKey(lastPressedKey ?: "")
        val newDirection = getDirectionFromKey(keyPressed)
        val indexOfLastDirection = Direction.values().indexOf(lastDirection)
        val indexOfNewDirection = Direction.values().indexOf(newDirection)

        val ranIntoSelf = (state.gameboard.snake.size > 1) &&
                    (indexOfLastDirection == indexOfNewDirection + if (indexOfNewDirection % 2 == 1) -1 else 1)

        if (isDifferentDirection && !ranIntoSelf) moveSnake(keyPressed)
    }

    store.subscribe { state = store.state }

    useEffect(state.gameState) {
        if (state.gameState == GameState.PLAYING) {
            lastPressedKey?.let {
                moveSnake(it)
                moveSnakeInterval = setInterval(state.settingsValues[Settings.TEMPO]!!.milliseconds) { moveSnake(it) }
            }
        } else if (state.gameState == GameState.PAUSED) {
            moveSnakeInterval?.let { clearInterval(it) }
        }
    }

    fun pressEscapeHandler() {
        if (state.gameState == GameState.PLAYING) {
            store.dispatch(SetGameStateAction(GameState.PAUSED))
        }
    }

    val keyDownHandler: (Event) -> Unit = { e ->
        val key = e.asDynamic().code as String
        if (key == "Escape") {
            pressEscapeHandler()
        } else if (state.gameState == GameState.PLAYING) {
            changeDirection(key)
        }
    }

    WindowHandler.addToWindow("gameViewHandler", "keydown", keyDownHandler)

    useEffect(lastPressedKey) {
        moveSnakeInterval?.let { clearInterval(it) }
        moveSnakeInterval = setInterval(state.settingsValues[Settings.TEMPO]!!.milliseconds) {
            lastPressedKey?.let { moveSnake(it) }
        }
    }

    fun endGameHandler() {
        lastPressedKey = null
        moveSnakeInterval?.let { clearInterval(it) }
        moveSnakeInterval = null
        store.dispatch(SetGameStateAction(GameState.OVER))
    }

    div {
        css {
            position = Position.relative
            margin = Auto.auto
            marginTop = 6.rem
            width = 70.vh
            height = 70.vh
        }

        div {
            css {
                width = 100.pct
                height = 100.pct
                outline = Outline((0.1.rem), LineStyle.solid, Color("gray"))
                display = Display.grid
                gridTemplateRows = repeat(state.settingsValues[Settings.SIZE]!!, 1.fr)
                gridTemplateColumns = repeat(state.settingsValues[Settings.SIZE]!!, 1.fr)
            }
            state.gameboard.board.flatten().map {
                div {
                    if (it.role == GridSpaceRoles.DEAD && state.gameState != GameState.OVER) endGameHandler()
                    css {
                        outline = Outline(0.1.rem, LineStyle.solid, Color("#0f0f0f"))
                        backgroundColor = when (it.role) {
                            GridSpaceRoles.DEAD -> Color(ReusableCSS.SNAKE_DEAD_COLOR)
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

        if (state.gameState == GameState.PAUSED) {
            section {
                css {
                    position = Position.fixed
                    top = 50.pct
                    left = 50.pct
                    transform = translate((-50).pct, (-50).pct)
                }
                PauseMenu()
            }
        }

        if (state.gameState == GameState.OVER) {
            section {
                css {
                    position = Position.fixed
                    top = 50.pct
                    left = 50.pct
                    transform = translate((-50).pct, (-50).pct)
                }
                GameOverMenu()
            }
        }
    }
}