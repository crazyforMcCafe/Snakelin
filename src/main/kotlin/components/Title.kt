package components

import csstype.Display
import csstype.rem
import emotion.react.css
import utils.ReusableCSS
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1

val Title = FC<Props> {
    h1 {
        css {
            ReusableCSS.centered(this)

            display = Display.inlineBlock
            fontSize = 4.rem
            marginBottom = (1.6).rem
        }
        +"Snakekotlin"
    }
}