package store.reducers

import org.reduxkotlin.Reducer
import store.AppState

enum class Settings { TEMPO, SIZE }

data class ChangeSettingAction(val setting: Settings, val settingIndex: Int)

val settingsReducer: Reducer<Map<Settings, Int>> = { state, action ->
    when (action) {
        is ChangeSettingAction -> {
            val newIndex = action.settingIndex.coerceIn(
                0..(AppState.settingsMaxIndices[action.setting]
                    ?: error("Max index not documented for setting '${action.setting}'"))
            )
            state.mapValues { (setting, index) -> if (setting == action.setting) newIndex else index }
        }
        else -> state
    }
}