package components.menu

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import utils.ReusableCSS
import kotlinx.browser.document
import react.ReactNode
import react.create
import react.dom.createPortal
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.h1

val Menu = FC<Props> {
    div {
        css {
            position = Position.absolute
            marginLeft = 2.rem
            marginTop = 3.rem
        }

        h1 {
            css {
                position = Position.relative
                fontSize = 6.rem
                marginBottom = (1.6).rem
                borderLeft = Border((0.2).rem, LineStyle.solid, Color("#fff"))
                paddingLeft = (1.2).rem
            }

            +"Snakelin"
        }

        HighscoreBoard()
    }
}