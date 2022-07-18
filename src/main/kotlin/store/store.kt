package store

import components.GridSpace
import org.reduxkotlin.Reducer
import store.reducers.GameState
import store.reducers.gameStateReducer
import store.reducers.settingsReducer
import store.reducers.snakeReducer

data class SettingsList(val tempoSetting: Int, val sizeSetting: Int)

data class AppState(
    val gameState: GameState?,
    val snake: List<GridSpace>,
    val settingsList: SettingsList
) {
    private val tempoMap = listOf(250, 180, 130, 80)
    private val sizeMap = listOf(10, 15, 20, 25)

    val tempoValue = tempoMap[settingsList.tempoSetting]
    val sizeValue = sizeMap[settingsList.sizeSetting]
}

val rootReducer: Reducer<AppState> = { state, action ->
    AppState(
        gameState = gameStateReducer(state.gameState, action),
        snake = snakeReducer(state.snake, action),
        settingsList = settingsReducer(state.settingsList, action)
    )
}