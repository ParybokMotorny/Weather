package com.example.menuhomework.viewmodels

import androidx.lifecycle.Observer
import com.example.menuhomework.model.Repository
import com.example.menuhomework.model.database.Weather
import com.example.menuhomework.model.database.search.Sortings
import com.example.menuhomework.viewStates.AppState
import com.example.menuhomework.viewStates.SearchViewState

class SearchViewModel(
    private val repository: Repository = Repository
) : BaseViewModel<List<Weather>, SearchViewState>() {

    private val notesObserver = Observer<List<Weather>> {
        _liveData.value = AppState.Success(SearchViewState(it))
    }

    //private val repositoryNotes = repository.getWeathers()

    init {
        repository.getWeathers().observeForever(notesObserver)
    }

    override fun onCleared() {
        repository.getWeathers().removeObserver(notesObserver)
    }

    fun deleteForId(id: Long){
        repository.deleteWeatherById(id)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun sort(type : Int) {
        when(type){
            Sortings.DATE -> repository.sortAllByDate(1)
            Sortings.DATEDESC -> repository.sortAllByDate(2)
            Sortings.NAME -> repository.sortAllByName(1)
            Sortings.NAMEDESC -> repository.sortAllByName(2)
        }
    }
}