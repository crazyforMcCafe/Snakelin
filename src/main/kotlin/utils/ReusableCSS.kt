package utils

import csstype.*

typealias CSS = PropertiesBuilder.() -> Unit

object ReusableCSS {
    const val SNAKE_HEAD_COLOR = "#fff"
    const val SNAKE_BODY_COLOR = "#738678"
    const val FOOD_COLOR = "#f10"
    const val GRID_BACKGROUND_COLOR = "#000"

    val styledButton: CSS = {
        padding = 0.rem
        color = Color("#fff")
        border = Border((0.1).rem, LineStyle.solid, Color("#fff"))
        background = None.none
        fontSize = 2.rem
        cursor = Cursor.pointer
    }
}