package components.gameMenu

import WindowHandler
import csstype.*
import emotion.react.css
import kotlinx.js.timers.setTimeout
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.events.Event
import react.*
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

external interface GenericMenuButtonProps : Props {
    var onClick: MouseEventHandler<HTMLButtonElement>
    var onUnpauseFromEsc: () -> Unit
    var text: String
}

val GenericMenuButton = FC<GenericMenuButtonProps> { props ->
    val handlerId = "pauseButtonHandler${this.hashCode()}"
    var pressed by useState(false)

    val keyDownHandler: (Event) -> Unit = { e ->
        val key = e.asDynamic().code as String
        if (key == "Escape" && !pressed && props.onUnpauseFromEsc != undefined) {
            pressed = true
            setTimeout(500.milliseconds) {
                props.onUnpauseFromEsc()
            }
        }
    }

    WindowHandler.addToWindow(handlerId, "keydown", keyDownHandler)

    rawUseEffect({
        return@rawUseEffect {
            WindowHandler.removeFromWindow(handlerId, "keydown")
        }
    })

    button {
        css {
            ReusableCSS.styledButton(this)
            ReusableCSS.invertColorWhenHovered(this)
            if (pressed) ReusableCSS.buttonFlashAnimation(this)
        }
        onClick = {
            if (!pressed) {
                pressed = true
                setTimeout(500.milliseconds) {
                    props.onClick(it)
                }
            }
        }
        +props.text
    }
}

external interface GenericMenuProps : Props {
    var text: String
    var buttons: List<ReactNode>
}

val GenericMenu = FC<GenericMenuProps> { props ->
    div {
        css {
            width = 28.rem
            padding = 0.4.rem
            backgroundColor = Color("#000")
            display = Display.flex
            flexDirection = FlexDirection.column
            outline = Outline(0.4.rem, LineStyle.solid, Color("#fff"))
        }
        p {
            css {
                fontSize = 2.8.rem
                textAlign = TextAlign.center
                marginBottom = 1.2.rem
            }
            +props.text
        }
        section {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceEvenly
            }
            props.buttons.map {
                +it
            }
        }
    }
}