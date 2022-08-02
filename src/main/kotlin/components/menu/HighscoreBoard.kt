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
        css {
            borderLeft = Border(0.2.rem, LineStyle.solid, Color("#fff"))
            paddingLeft = (1.2).rem
            backgroundImage = "linear-gradient(to right, #0a0a0a, transparent 40%)".unsafeCast<BackgroundImage>()
        }

        p {
            css {
                fontSize = (2.4).rem
                marginBottom = (0.2).rem
                borderBottom = Border((0.8).rem, LineStyle.double, Color("#fff"))
                width = 40.rem
            }
            +"High Scores:"
        }

        div {
            css {
                display = Display.grid
                gridTemplateColumns = "8rem 1fr".unsafeCast<GridTemplateTracks>()
                fontSize = (2.2).rem
                marginLeft = (-1.2).rem
                overflow = Overflow.hidden
            }

            section {
                css {
                    marginLeft = (2.6).rem
                }
                (1..DUMMY_SCORES.size).forEach {
                    section {
                        css { zIndex = integer(2); position = Position.relative }
                        if (it in 1..3) div {
                            css {
                                position = Position.absolute
                                background =
                                    "radial-gradient(ellipse closest-side, ${
                                        when (it) {
                                            1 -> "hsl(51deg 100% 15%)"
                                            2 -> "hsl(51deg 0% 21%)"
                                            3 -> "hsl(39deg 26% 18%)"
                                            else -> "black"
                                        }
                                    }, transparent)".unsafeCast<Background>()
                                width = 500.pct
                                height = 100.pct
                                left = (-107).pct
                                zIndex = integer(-1)
                            }
                        }
                        p { +"$it." }
                    }
                }
            }
            section {
                css { zIndex = integer(2) }
                DUMMY_SCORES.sortedDescending().forEach {
                    p { +"$it" }
                }
            }
        }
    }
}