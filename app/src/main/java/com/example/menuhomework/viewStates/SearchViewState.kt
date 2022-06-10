package com.example.menuhomework.viewStates

import com.example.menuhomework.model.database.Weather

class SearchViewState(override val data: List<Weather>) :
    AppStateEntity<List<Weather>>