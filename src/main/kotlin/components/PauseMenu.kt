package components

import Store
import components.gameMenu.GenericMenu
import components.gameMenu.GenericMenuButton
import react.FC
import react.Props
import react.create
import store.reducers.GameState
import store.reducers.SetGameStateAction


val PauseMenu = FC<Props> {
    val store = Store.appStore

    GenericMenu {
        text = "Paused"
        buttons = listOf(
            GenericMenuButton.create {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                onUnpauseFromEsc = {
                    store.dispatch(SetGameStateAction(GameState.PLAYING))
                }
                text = "Resume"
            },
            GenericMenuButton.create {
                onClick = {
                    store.dispatch(SetGameStateAction(GameState.NONE))
                }
                text = "Quit"
            }
        )
    }
}