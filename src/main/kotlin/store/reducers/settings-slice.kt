package store.reducers

import org.reduxkotlin.Reducer
import store.SettingsList

data class ChangeSettingAction(val setting: String, val value: Int)

val settingsReducer: Reducer<SettingsList> = { state, action -> state }