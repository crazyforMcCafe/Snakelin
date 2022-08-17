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
                    // TODO: End game
                    null
                }
            }

            newHead?.let {
                if (it.role == GridSpaceRoles.SNAKE_BODY) {
                    // TODO: End game
                    return@let state
                }

                var newFood = state.food

                val newSnake = if(it.role == GridSpaceRoles.FOOD) {
                    newFood = newFood - it + state.board.randomEmptyGridSpace(GridSpaceRoles.FOOD)
                    listOf(it) + state.snake
                } else {
                    listOf(it) + state.snake.dropLast(1)
                }

                it.role = GridSpaceRoles.SNAKE_HEAD

                Gameboard(
                    rowSize = state.rowSize,
                    columnSize = state.columnSize,
                    snake = newSnake,
                    food = newFood
                )
            } ?: state
        }
        else -> state
    }
}