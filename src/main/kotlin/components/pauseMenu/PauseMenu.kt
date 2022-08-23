package components.pauseMenu

import csstype.*
import emotion.react.css
import kotlinx.js.timers.setTimeout
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.useState
import store.reducers.GameState
import store.reducers.SetGameStateAction
import utils.ReusableCSS
import kotlin.time.Duration.Companion.milliseconds

external interface PauseMenuButtonProps : Props {
    var onClick: MouseEventHandler<HTMLButtonElement>
    var text: String
}

val PauseMenuButton = FC<PauseMenuButtonProps> { props ->
    var pressed by useState(false)

    button {
        css {
            ReusableCSS.styledButton(this)
            ReusableCSS.invertColorWhenHovered(this)
            if (pressed) {
                console.log("FLASHING")
                ReusableCSS.buttonFlashAnimation(this)
            }
        }
        onClick = {
            pressed = true
            setTimeout(500.milliseconds) {
                props.onClick(it)
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
                    console.log("Pressed RESUME button")
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                text = "Resume"
            }
            PauseMenuButton {
                onClick = {
                    console.log("Pressed QUIT button")
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                text = "Quit"
            }
        }
    }
}