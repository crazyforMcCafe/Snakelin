package components

import csstype.*
import emotion.react.css
import kotlinx.browser.window
import utils.ReusableCSS
import org.w3c.dom.events.Event
import react.*
import react.dom.html.ReactHTML.div
import store.AppState
import store.reducers.InitSnakeAction
import kotlin.random.Random

data class GridSpace(val row: Int, val column: Int) {
    var isSnakeHead = false
    var isSnakeBody = false
    var isSnakePart = false
    var isFood = isSnakeBody || isSnakeBody

    override operator fun equals(other: Any?): Boolean {
        return if (other is GridSpace) this.row == other.row && this.column == other.column else false
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + column
        return result
    }
}

val Gameboard = FC<Props> {
    val store = Store.appStore
    var state: AppState by useState(store.state)
    val snake = state.snake
    val size = state.sizeValue

    val gridSpaces: List<GridSpace> by useState(kotlin.run {
        val gridSpaces = mutableListOf<GridSpace>()
        (0..size).forEach { row ->
            (0..size).forEach { col ->
                gridSpaces.add(GridSpace(row, col))
            }
        }
        gridSpaces.toList()
    })

    val unsubscribe = store.subscribe { state = store.state }

    fun getRandomGridSpace(): GridSpace {
        val row = Random.nextInt(0, size)
        val column = Random.nextInt(0, size)
        return gridSpaces.find {
            it == GridSpace(row, column)
        }!!
    }

    useEffectOnce {
        store.dispatch(InitSnakeAction(getRandomGridSpace()))
    }

    snake.forEachIndexed { i, snakePart ->
        val gridSpace = gridSpaces.find { it == snakePart }!!
        if (i == 0) gridSpace.isSnakeHead = true else gridSpace.isSnakeBody = true
    }

    div {
        css {
            ReusableCSS.centered(this)

            outline = Outline(((0.1).rem), LineStyle.solid, Color("gray"))
            width = 70.vh
            height = 70.vh
            display = Display.grid
            gridTemplateRows = repeat(size, 1.fr)
            gridTemplateColumns = repeat(size, 1.fr)
        }

        gridSpaces.map {
            div {
                css {
                    outline = Outline(((0.1).rem), LineStyle.solid, Color("#0f0f0f"))
                    backgroundColor = when {
                        it.isSnakeHead -> Color(ReusableCSS.SNAKE_HEAD_COLOR)
                        it.isSnakeBody -> Color(ReusableCSS.SNAKE_BODY_COLOR)
                        it.isFood -> Color(ReusableCSS.FOOD_COLOR)
                        else -> Color(ReusableCSS.GRID_BACKGROUND_COLOR)
                    }
                }
                id = "gridSpace__${it.row}-${it.column}"
            }
        }
    }
}