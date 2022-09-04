package store.reducers

import org.reduxkotlin.Reducer
import store.Gameboard
import store.GridSpaceRoles
import store.randomEmptyGridSpace

class InitBoardAction(val rowSize: Int, val columnSize: Int) {
    constructor(boardSize: Int) : this(boardSize, boardSize)
}

data class MoveSnakeAction(val direction: Direction)
enum class Direction { UP, DOWN, LEFT, RIGHT }

val gameboardReducer: Reducer<Gameboard> = { state, action ->
    when (action) {
        is InitBoardAction -> Gameboard(action.rowSize, action.columnSize)
        is MoveSnakeAction -> {
            val prevHead = state.snake[0]
            val newHead = with (prevHead) {
                try {
                    when (action.direction) {
                        Direction.UP -> state.board[row - 1][column]
                        Direction.DOWN -> state.board[row + 1][column]
                        Direction.LEFT -> state.board[row][column - 1]
                        Direction.RIGHT -> state.board[row][column + 1]
                    }
                } catch (_: IndexOutOfBoundsException) {
                    null
                }
            }

            var newFoodSet = state.food
            val newSnake: List<Gameboard.GridSpace> = newHead?.let {
                val newSnake = when (it.role) {
                    GridSpaceRoles.SNAKE_BODY -> {
                        return@let state.snake.map { gridSpace ->  gridSpace.copy(role = GridSpaceRoles.DEAD) }
                    }
                    GridSpaceRoles.FOOD -> {
                        newFoodSet = newFoodSet - it + state.board.randomEmptyGridSpace(GridSpaceRoles.FOOD)
                        listOf(it) + state.snake.map { gridSpace ->  gridSpace.copy(role = GridSpaceRoles.SNAKE_BODY) }
                    }
                    else -> listOf(it) + state.snake.dropLast(1).map { gridSpace ->  gridSpace.copy(role = GridSpaceRoles.SNAKE_BODY) }
                }

                it.role = GridSpaceRoles.SNAKE_HEAD

                newSnake
            } ?: state.snake.map { gridSpace ->  gridSpace.copy(role = GridSpaceRoles.DEAD) }

            Gameboard(
                rowSize = state.rowSize,
                columnSize = state.columnSize,
                food = newFoodSet,
                snake = newSnake
            )
        }
        else -> state
    }
}