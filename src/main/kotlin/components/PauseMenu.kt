package components

import Store
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
import store.reducers.GameState
import store.reducers.SetGameStateAction
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

external interface PauseMenuButtonProps : Props {
    var onClick: MouseEventHandler<HTMLButtonElement>
    var onUnpauseFromEsc: () -> Unit
    var text: String
}

val PauseMenuButton = FC<PauseMenuButtonProps> { props ->
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

val PauseMenu = FC<Props> {
    val store = Store.appStore

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
            +"Paused"
        }
        section {
            css {
                display = Display.flex
                justifyContent = JustifyContent.spaceEvenly
//                gap = 0.8.rem
            }
            PauseMenuButton {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                onUnpauseFromEsc = {
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                text = "Resume"
            }
            PauseMenuButton {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.NONE))
                }
                text = "Quit"
            }
        }
    }
}