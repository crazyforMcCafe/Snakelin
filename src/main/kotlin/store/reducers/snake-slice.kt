package store.reducers

import components.GridSpace
import org.reduxkotlin.Reducer

enum class Direction { UP, DOWN, LEFT, RIGHT }

data class InitSnakeAction(val gridSpace: GridSpace)
data class MoveSnakeAction(val direction: Direction)

val snakeReducer: Reducer<List<GridSpace>> = { state, action ->
    when (action) {
        is InitSnakeAction -> listOf(action.gridSpace)
        is MoveSnakeAction -> state
        else -> state
    }
}