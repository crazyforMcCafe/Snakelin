package store.reducers

import org.reduxkotlin.Reducer

enum class GameState {PLAYING,PAUSED,OVER}
data class ChangeGameStateAction(val gameState: GameState)

val gameStateReducer: Reducer<GameState?> = { state, action -> state }