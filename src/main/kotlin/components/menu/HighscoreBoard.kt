package components.menu

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.span

val DUMMY_SCORES = listOf(100, 200, 300, 400)

val HighscoreBoard = FC<Props> {
    div {
        p {
            css {
                fontSize = 2.rem
                marginBottom = (0.2).rem
                borderBottom = Border((0.8).rem, LineStyle.double, Color("#fff"))
            }
            +"High Scores:"
        }

        div {
            css {
                display = Display.grid
                gridTemplateColumns = "min-content 1fr".unsafeCast<GridTemplateTracks>()
                fontSize = (1.6).rem
                marginLeft = (2.6).rem
            }

            section {
                (1..DUMMY_SCORES.size).forEach {
                    p { +"$it." }
                }
            }
            section {
                DUMMY_SCORES.forEach {
                    p { +"$it" }
                }
            }
        }
    }
}