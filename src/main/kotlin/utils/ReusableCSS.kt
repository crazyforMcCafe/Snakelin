package utils

import csstype.*
import kotlinx.browser.window

val VIEWPORT_WIDTH = window.length
val VIEWPORT_HEIGHT = window.innerHeight

object ReusableCSS {
    const val SNAKE_HEAD_COLOR = "#fff"
    const val SNAKE_BODY_COLOR = "#738678"
    const val FOOD_COLOR = "#f10"
    const val GRID_BACKGROUND_COLOR = "#000"

    val centered: PropertiesBuilder.() -> Unit = {
        marginLeft = 50.vw
        transform = translate((-50).pct)
    }
}