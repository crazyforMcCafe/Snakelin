package store

enum class GridSpaceRoles { EMPTY, SNAKE_HEAD, SNAKE_BODY, FOOD, DEAD }

typealias Board = List<List<Gameboard.GridSpace>>
fun Board.randomEmptyGridSpace(setRoleTo: GridSpaceRoles = GridSpaceRoles.EMPTY): Gameboard.GridSpace {
    val gridSpace = flatten().filter { gridSpace -> gridSpace.role == GridSpaceRoles.EMPTY }.random()
    gridSpace.role = setRoleTo
    return gridSpace
}

class Gameboard(
    val rowSize: Int,
    val columnSize: Int,
    val board: Board = List(rowSize) { row ->
        List(columnSize) { column ->
            GridSpace(row, column)
        }
    },
    val snake: List<GridSpace> = listOf(board.randomEmptyGridSpace(GridSpaceRoles.SNAKE_HEAD)),
    val food: Set<GridSpace> = setOf(board.randomEmptyGridSpace(GridSpaceRoles.FOOD))
) {

    init {
        snake.forEach {
            it.apply { board[row][column].role = if (role == GridSpaceRoles.EMPTY) GridSpaceRoles.SNAKE_BODY else role }
        }
        food.forEach {
            it.apply { board[row][column].role = GridSpaceRoles.FOOD }
        }
    }

    data class GridSpace(val row: Int, val column: Int, var role: GridSpaceRoles = GridSpaceRoles.EMPTY) {

        var isPartOfSnake = role == GridSpaceRoles.SNAKE_BODY || role == GridSpaceRoles.SNAKE_HEAD
        var isFood = role == GridSpaceRoles.FOOD

        override operator fun equals(other: Any?): Boolean =
            if (other is GridSpace) this.row == other.row && this.column == other.column else false

        override fun hashCode(): Int {
            var result = row
            result = 31 * result + column
            return result
        }
    }
}