package store

import org.reduxkotlin.Reducer
import store.reducers.*


data class AppState(
    val gameState: GameState?,
    val settingsIndices: Map<Settings, Int>,
    val gameboard: Gameboard
) {
    companion object {
        private val tempoMap = listOf(150, 125, 100, 75, 50)
        private val sizeMap = listOf(10, 15, 20, 25)

        val settingsMaxIndices = mapOf(Settings.TEMPO to tempoMap.lastIndex, Settings.SIZE to sizeMap.lastIndex)
    }

    val settingsValues = mapOf(
        Settings.TEMPO to tempoMap[settingsIndices[Settings.TEMPO]!!],
        Settings.SIZE to sizeMap[settingsIndices[Settings.SIZE]!!]
    )
}

val rootReducer: Reducer<AppState> = { state, action ->
    AppState(
        gameState = gameStateReducer(state.gameState, action),
        settingsIndices = settingsReducer(state.settingsIndices, action),
        gameboard = gameboardReducer(state.gameboard, action)
    )
}