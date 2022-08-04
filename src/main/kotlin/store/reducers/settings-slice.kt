package store.reducers

import org.reduxkotlin.Reducer
import store.AppState
import store.SettingsList

enum class Settings { TEMPO, SIZE }

data class ChangeSettingAction(val setting: Settings, val settingIndex: Int)

val settingsReducer: Reducer<SettingsList> = { state, action ->
    when (action) {
        is ChangeSettingAction -> {
            val newIndex = action.settingIndex.coerceIn(
                0..(AppState.settingsMaxIndices[action.setting]
                    ?: error("Max index does not exist for setting ${action.setting}"))
            )
            when (action.setting) {
                Settings.TEMPO -> state.copy(tempoSetting = newIndex)
                Settings.SIZE -> state.copy(sizeSetting = newIndex)
            }
        }
        else -> state
    }
}