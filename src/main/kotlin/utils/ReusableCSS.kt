package utils

import csstype.*
import emotion.css.keyframes

typealias CSS = PropertiesBuilder.() -> Unit

object ReusableCSS {
    const val SNAKE_HEAD_COLOR = "#fff"
    const val SNAKE_BODY_COLOR = "#738678"
    const val FOOD_COLOR = "#f10"
    const val GRID_BACKGROUND_COLOR = "#000"

    val styledButton: CSS = {
        padding = 0.4.rem
        color = Color("#fff")
        border = Border((0.1).rem, LineStyle.solid, Color("#fff"))
        background = None.none
        fontSize = 2.rem
        cursor = Cursor.pointer
    }

    val animatedWhenPressedDown: CSS = {
        transition = "transform 100ms ease-out".unsafeCast<Transition>()

        hover {
            transform = translatey(0.4.em)
        }

        active {
            transition = None.none
            transform = translatey(0.8.em)
        }
    }

    val invertColorWhenHovered: CSS = {
        hover {
            color = Color("#000")
            backgroundColor = Color("#fff")
        }
    }

    val buttonFlashAnimation: CSS = {
        val pressButtonAni = keyframes {
            0.pct {
                backgroundColor = Color("#000")
                color = Color("#fff")
            }
            50.pct {
                backgroundColor = Color("#fff")
                color = Color("#000")
            }
        }

        animationName = pressButtonAni
        animationDuration = "200ms".unsafeCast<AnimationDuration>()
        animationTimingFunction = "cubic-bezier(1, 0, 1, 0)".unsafeCast<AnimationTimingFunction>()
        animationIterationCount = AnimationIterationCount.infinite
    }
    val slideInFromLeftAnimation: CSS = {
        val slideInAni = keyframes {
            0.pct {
                left = (-100).rem
            }
            100.pct {
                left = 1.2.rem
            }
        }

        animationName = slideInAni
        animationDuration = "1s".unsafeCast<AnimationDuration>()
        animationTimingFunction = "cubic-bezier(0.02, 0.32, 0.58, 1)".unsafeCast<AnimationTimingFunction>()
    }
}