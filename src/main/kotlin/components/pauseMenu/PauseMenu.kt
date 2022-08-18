package components.pauseMenu

import csstype.Display
import csstype.FlexDirection
import csstype.rem
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import utils.ReusableCSS

val PauseMenu = FC<Props> {
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        p {
            css {
                fontSize = (2.4).rem
            }
            +"Paused"
        }
        section {
            css {
                display = Display.flex
            }
            button {
                css {
                    ReusableCSS.styledButton(this)
                }
                +"Resume"
            }
            button {
                css {
                    ReusableCSS.styledButton(this)
                }
                +"Quit"
            }
        }
    }
}