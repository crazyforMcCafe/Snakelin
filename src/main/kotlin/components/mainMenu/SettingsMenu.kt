package components.mainMenu

import Store
import csstype.*
import emotion.react.css
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.useEffect
import react.useState
import store.reducers.ChangeSettingAction
import store.reducers.Settings
import utils.ReusableCSS

external interface SettingSetterProps : Props {
    var settingId: Settings
    var displayName: String
    var options: List<String>
}

external interface SettingEditButtonProps : Props {
    var right: Boolean
    var left: Boolean
    var onClick: MouseEventHandler<HTMLButtonElement>
}

val SettingEditButton = FC<SettingEditButtonProps> { props ->
    button {
        onClick = props.onClick
        css {
            padding = 0.rem
            color = Color("#fff")
            border = Border((0.1).rem, LineStyle.solid, Color("#fff"))
            background = None.none
            fontSize = 2.rem
            cursor = Cursor.pointer

            width = 100.pct
            fontFamily = FontFamily.serif

            ReusableCSS.animatedWhenPressedDown(this)
        }
        div {
            css {
                width = 100.pct
                height = 100.pct
                display = Display.flex
                justifyContent = JustifyContent.center
                alignContent = AlignContent.center
                flexDirection = FlexDirection.column
            }
            +(if (props.left) "\u25C0" else if (props.right) "\u25B6" else "") // ◀ and ▶
        }
    }
}

val SettingSetter = FC<SettingSetterProps> { props ->
    val store = Store.appStore
    var state by useState(store.state)
    var settingIndex by useState(0)

    store.subscribe { state = store.state }

    useEffect(state.settingsIndices) {
        settingIndex = state.settingsIndices[props.settingId] ?: error("No index is documented for setting '${props.settingId}'")
    }

    fun changeSetting(indexAdjustment: Int) {
        val newIndex = settingIndex + indexAdjustment
        store.dispatch(ChangeSettingAction(props.settingId, newIndex))
    }

    div {
        css { color = Color("#fff") }
        p {
            css {
                fontSize = 2.rem
                textAlign = TextAlign.center
            }
            +props.displayName
        }
        section {
            // In rem
            val wholeWidth = 25
            val buttonWidth = 2.4
            val labelWidth = wholeWidth - (buttonWidth * 2)

            css {
                width = 25.rem
                display = Display.grid
                gridTemplateColumns = "${buttonWidth}rem ${labelWidth}rem ${buttonWidth}rem".unsafeCast<GridTemplateColumns>()
                margin = Auto.auto
            }
            SettingEditButton {
                left = true
                onClick = {
                    changeSetting(-1)
                }
            }
            div {
                css {
                    border = Border((0.1).rem, LineStyle.solid, Color("#fff"))
                    overflow = Overflow.hidden
                    display = Display.grid
                    gridTemplateColumns = repeat(props.options.size, labelWidth.rem)
                    alignItems = AlignItems.stretch
                }
                props.options.map {
                    p {
                        css {
                            fontSize = (1.8).rem
                            textAlign = TextAlign.center
                            paddingTop = (0.2).rem

                            transition = "transform 100ms ease-out".unsafeCast<Transition>()
                            transform = translatex(-(settingIndex * labelWidth).rem)
                        }
                        +it
                    }
                }
            }
            SettingEditButton {
                right = true
                onClick = {
                    changeSetting(1)
                }
            }
        }
    }
}

val SettingsMenu = FC<Props> {
    div {
        css {
            width = 30.rem
            height = 100.pct
            border = Border((0.8).rem, LineStyle.double, Color("#fff"))
            display = Display.flex
            flexDirection = FlexDirection.column

        }
        p {
            css {
                textAlign = TextAlign.center
                fontSize = (2.4).rem
                borderBottom = Border((0.27).rem, LineStyle.solid, Color("#fff"))
            }
            +"Settings"
        }
        section {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.spaceAround
                alignContent = AlignContent.center
                flex = Flex(number(1.0), number(1.0), Auto.auto)
            }
            SettingSetter {
                settingId = Settings.TEMPO
                displayName = "Tempo"
                options = listOf("Slow", "Moderate", "Swifty", "Fast", "SPEED")
            }
            SettingSetter {
                settingId = Settings.SIZE
                displayName = "Size"
                options = listOf("Small", "Medium", "Large", "Biggest")

            }
        }

    }
}