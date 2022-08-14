package components.menu

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.section

val MainMenu = FC<Props> {
    div {
        css {
            position = Position.relative
            width = 100.pct
        }

        h1 {
            css {
                marginLeft = 2.rem
                marginTop = 3.rem

                position = Position.relative
                fontSize = 6.rem
                marginBottom = (1.6).rem
                borderLeft = Border((0.2).rem, LineStyle.solid, Color("#fff"))
                paddingLeft = (1.2).rem

                backgroundImage = "linear-gradient(to right, #333399, transparent 50%)".unsafeCast<BackgroundImage>()
            }

            +"Snakelin"
        }

        section {
            css {
                display = Display.grid
                gridTemplateColumns = "1fr 1fr".unsafeCast<GridTemplateColumns>()
                width = 100.pct
            }
            div {
                css {
                    gridArea = "1 / 1 / span 1 / span 1".unsafeCast<GridArea>()
                    marginLeft = 2.rem
                }
                HighscoreBoard()
            }
            div {
                css {
                    gridArea = "1 / 2 / span 1 / span 1".unsafeCast<GridArea>()
                    marginRight = 2.rem
                    justifySelf = JustifySelf.center
                    height = 110.pct
                    alignSelf = AlignSelf.center
                }
                SettingsMenu()
            }
        }
    }
}