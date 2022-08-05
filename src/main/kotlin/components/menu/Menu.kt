package components.menu

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
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

                backgroundImage = "linear-gradient(to right, #333399, transparent)".unsafeCast<BackgroundImage>()
            }

            +"Snakelin"
        }

        HighscoreBoard()
        SettingsMenu()
    }
}