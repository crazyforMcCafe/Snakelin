package components.menu

import csstype.Display
import csstype.FlexDirection
import csstype.GridTemplateColumns
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.section
import react.useState
import store.reducers.ChangeSettingAction
import store.reducers.Settings

external interface SettingSetterProps : Props {
    var settingId: Settings
    var displayName: String
    var options: List<String>
}

val SettingSetter = FC<SettingSetterProps> { props ->
    val store = Store.appStore
    var state by useState(store.state)

    val settingIndex = state.settingsIndices[props.settingId] ?: error("No index is documented for setting '${props.settingId}'")

    store.subscribe {
        state = store.state
    }

    fun changeSetting(indexAdjustment: Int) {
        val newIndex = settingIndex + indexAdjustment
        store.dispatch(ChangeSettingAction(props.settingId, newIndex))
    }

    div {
        p { +props.displayName }
        section {
            css {
                display = Display.grid
                gridTemplateColumns = "5rem 1fr 5rem".unsafeCast<GridTemplateColumns>()
            }
            button {
                onClick = {
                    changeSetting(-1)
                }
                +"\u25C0"
            }
            p {
                +props.options[settingIndex]
            }
            button {
                onClick = {
                    changeSetting(1)
                }
                +"\u25B6"
            }
        }
    }
}

val SettingsMenu = FC<Props> {
    div {
        p {
            +"Settings"
        }
        section {
            css { display = Display.flex; flexDirection = FlexDirection.column }
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