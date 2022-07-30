package store

import org.reduxkotlin.Reducer
import store.reducers.*

data class SettingsList(val tempoSetting: Int, val sizeSetting: Int)

data class AppState(
    val gameState: GameState?,
    val settingsList: SettingsList,
    val gameboard: Gameboard
) {
    private val tempoMap = listOf(150, 125, 100, 75, 50)
    private val sizeMap = listOf(10, 15, 20, 25)

    val tempoValue = tempoMap[settingsList.tempoSetting]
    val sizeValue = sizeMap[settingsList.sizeSetting]
}

val rootReducer: Reducer<AppState> = { state, action ->
    AppState(
        gameState = gameStateReducer(state.gameState, action),
        settingsList = settingsReducer(state.settingsList, action),
        gameboard = gameboardReducer(state.gameboard, action)
    )
}