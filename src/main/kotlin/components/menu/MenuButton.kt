package components.menu

import csstype.*
import emotion.react.css
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div

external interface  MenuButtonProps : Props {
    var text: String
    var onClick: MouseEventHandler<HTMLButtonElement>
}

val MenuButton = FC<MenuButtonProps> { props ->
    button {
        css {
            padding = 0.rem
            color = Color("#fff")
            border = Border((0.1).rem, LineStyle.solid, Color("#fff"))
            background = None.none
            fontSize = 2.rem

            width = 100.pct
        }
        onClick = props.onClick
        div {
            css {
                width = 100.pct
                height = 100.pct
                display = Display.flex
                justifyContent = JustifyContent.center
                alignContent = AlignContent.center
                flexDirection = FlexDirection.column
            }
            +props.text
        }
    }
}