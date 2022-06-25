package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Weather

class HistoryViewState(override val data: List<Weather>) :
    AppStateEntity<List<Weather>>