package components.mainMenu

import csstype.*
import emotion.react.css
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.section
import utils.ReusableCSS

external interface MainMenuProps : Props {
    var onPlayButtonClick:  MouseEventHandler<HTMLButtonElement>
}

val MainMenu = FC<MainMenuProps> { props ->
    div {
        css {
            position = Position.absolute
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

                color = rgba(255,255,255,0.0)

                after {
                    content = "''".unsafeCast<Content>()
                    position = Position.absolute
                    backgroundColor = Color("black")
                    height = 100.pct
                    width = 40.rem
                    left = (-40 + -0.2).rem // Must subtract the width of borderLeft

                }

                before {
                    content = "'Snakelin'".unsafeCast<Content>()
                    position = Position.absolute
                    color = Color("#fff")
                    left = 1.2.rem

                    ReusableCSS.slideInFromLeftAnimation(0.ms, 1.2.rem)(this)
                }
            }

            +"Snakelin" // Makes the sizing right
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
                    marginRight = 8.rem
                    justifySelf = JustifySelf.right
                    height = 110.pct
                    alignSelf = AlignSelf.center
                }
                SettingsMenu()
            }
        }
        button {
            css {
                border = None.none
                background = None.none
                display = Display.block
                position = Position.fixed
                bottom = 0.rem
                width = 100.vw
                height = 8.rem
                fontSize = 5.rem
                textAlign = TextAlign.center
                boxShadow = BoxShadow(BoxShadowInset.inset, 0.rem, 0.rem, 0.rem, 0.2.rem, Color("#fff"))
                fontStyle = FontStyle.italic
                letterSpacing = 1.2.rem
                color = Color("#fff")
                cursor = Cursor.pointer
            }
            onClick = props.onPlayButtonClick

            +"PLAY"
        }
    }
}