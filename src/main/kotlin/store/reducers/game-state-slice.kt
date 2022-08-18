package store.reducers

import org.reduxkotlin.Reducer

enum class GameState {PLAYING,PAUSED,OVER,NONE}
data class SetGameStateAction(val gameState: GameState)

val gameStateReducer: Reducer<GameState> = { state, action ->
    when (action) {
        is SetGameStateAction -> action.gameState
        else -> state
    }
}