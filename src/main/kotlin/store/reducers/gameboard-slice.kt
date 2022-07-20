package store.reducers

import org.reduxkotlin.Reducer
import kotlin.random.Random

enum class GridSpaceRoles { EMPTY, SNAKE_HEAD, SNAKE_BODY, FOOD }

data class Gameboard(
    val rowSize: Int,
    val columnSize: Int,
    val board: List<GridSpace> = run {
        val gridSpaces = mutableListOf<GridSpace>()
        (0 until rowSize).forEach { row ->
            (0 until columnSize).forEach { column ->
                gridSpaces.add(GridSpace(row, column))
            }
        }
        gridSpaces.toList()
    },
    val snake: List<GridSpace> = listOf(),
    val food: Set<GridSpace> = setOf()
) {

    data class GridSpace(val row: Int, val column: Int, var role: GridSpaceRoles = GridSpaceRoles.EMPTY) {

        var isPartOfSnake = role == GridSpaceRoles.SNAKE_BODY || role == GridSpaceRoles.SNAKE_HEAD
        var isFood = role == GridSpaceRoles.FOOD

        override operator fun equals(other: Any?): Boolean {
            return if (other is GridSpace) this.row == other.row && this.column == other.column else false
        }

        override fun hashCode(): Int {
            var result = row
            result = 31 * result + column
            return result
        }
    }

    fun randomGridSpace(setRoleTo: GridSpaceRoles = GridSpaceRoles.EMPTY): GridSpace {
        val numEmptySpaces = board.count { gridSpace -> gridSpace.role == GridSpaceRoles.EMPTY }
        val gridSpace = board.filter { gridSpace -> gridSpace.role == GridSpaceRoles.EMPTY }[Random.nextInt(
            0,
            numEmptySpaces
        )]
        gridSpace.role = setRoleTo
        return gridSpace
    }
}

class InitBoardAction(val rowSize: Int, val columnSize: Int) {
    constructor(boardSize: Int) : this(boardSize, boardSize)
}

data class MoveSnakeAction(val direction: Direction)
enum class Direction { UP, DOWN, LEFT, RIGHT }

val gameboardReducer: Reducer<Gameboard> = { state, action ->
    when (action) {
        is InitBoardAction -> Gameboard(action.rowSize, action.columnSize)
            .run {
                copy(snake = listOf(randomGridSpace(GridSpaceRoles.SNAKE_HEAD)))
            }.run {
                copy(food = setOf(randomGridSpace(GridSpaceRoles.FOOD)))
            }
        is MoveSnakeAction -> {
            val prevHead = state.snake[0]
            val newHead = when (action.direction) {
                Direction.UP -> Gameboard.GridSpace(
                    prevHead.row - 1,
                    prevHead.column,
                    GridSpaceRoles.SNAKE_HEAD
                )
                Direction.DOWN -> Gameboard.GridSpace(
                    prevHead.row + 1,
                    prevHead.column,
                    GridSpaceRoles.SNAKE_HEAD
                )
                Direction.LEFT -> Gameboard.GridSpace(
                    prevHead.row,
                    prevHead.column - 1,
                    GridSpaceRoles.SNAKE_HEAD
                )
                Direction.RIGHT -> Gameboard.GridSpace(
                    prevHead.row,
                    prevHead.column + 1,
                    GridSpaceRoles.SNAKE_HEAD
                )
            }

            console.log("previous head = $prevHead")
            console.log("new head = $newHead")

            if (newHead.row < 0 || newHead.column < 0) {
                // TODO: End Game
                state
            } else {
                state.copy(snake = listOf(newHead))
            }
        }
        else -> state
    }
}