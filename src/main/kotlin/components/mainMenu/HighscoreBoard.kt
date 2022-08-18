package components.mainMenu

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section

val DUMMY_SCORES = listOf<ULong>()

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
                borderTop = Border((0.8).rem, LineStyle.double, Color("#fff"))
            }

            section {
                css { gridColumn = "1 / 2 / span 1 / span 1".unsafeCast<GridArea>() }
                if (DUMMY_SCORES.isEmpty()) {
                    p {
                        css { color = Color("green"); width = 20.rem}
                        +"Play some games and get some scores!"
                    }
                } else {
                    css { zIndex = integer(2) }
                    DUMMY_SCORES.sortedDescending().forEachIndexed { index, score ->
                        section {
                            css { position = Position.relative }
                            if (index in 0..2)
                                div {
                                    css {
                                        position = Position.absolute
                                        background =
                                            "radial-gradient(ellipse closest-side, ${
                                                when (index) {
                                                    0 -> "hsl(51deg 100% 15%)"
                                                    1 -> "hsl(51deg 0% 21%)"
                                                    2 -> "hsl(39deg 26% 18%)"
                                                    else -> "black"
                                                }
                                            }, transparent)".unsafeCast<Background>()
                                        width = 75.pct
                                        height = 100.pct
                                        left = (-25).pct
                                        zIndex = integer(-1)
                                    }
                                }
                            p { +"$score" }
                        }
                    }
                }
            }
            section {
                css {
                    marginLeft = (1.2).rem
                    gridArea = "1 / 1 / span 1 / span 1".unsafeCast<GridArea>()
                }
                (1..5).forEach {
                    p {
                        css { zIndex = integer(2); position = Position.relative }
                        +"$it."
                    }
                }
            }
        }
    }
}