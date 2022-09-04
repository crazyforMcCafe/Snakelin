package components.gameMenu

import Store
import react.FC
import react.Props
import react.create
import store.reducers.GameState
import store.reducers.InitBoardAction
import store.reducers.SetGameStateAction
import store.reducers.Settings


val GameOverMenu = FC<Props> {
    val store = Store.appStore

    GenericMenu {
        text = "Game Over :("
        buttons = listOf(
            GenericMenuButton.create {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                    store.dispatch(
                        InitBoardAction(
                            store.state.settingsValues[Settings.SIZE]
                                ?: error("Could not initialize board: ${Settings.SIZE} not found in settingsValues")
                        )
                    )
                }
                text = "Play"
            },
            GenericMenuButton.create {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.NONE))
                }
                onUnpauseFromEsc = {
                    store.dispatch(SetGameStateAction(GameState.NONE))
                }
                text = "Quit"
            }
        )
    }
}