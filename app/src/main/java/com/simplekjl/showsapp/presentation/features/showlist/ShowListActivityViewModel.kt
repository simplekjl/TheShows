package com.simplekjl.showsapp.presentation.features.showlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simplekjl.showsapp.domain.Repository
import com.simplekjl.showsapp.presentation.features.showlist.mapper.StateMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowListActivityViewModel constructor(private val repository: Repository, private val stateMapper: StateMapper) :
    ViewModel() {
    private var compositeDisposable = CompositeDisposable()

    var loadingData = false
    var nextPage = 0
    var totalPages = 0

    val shows: MutableLiveData<ShowListViewState> = MutableLiveData()

    fun getShows(page: Int): LiveData<ShowListViewState> {
        val data: MutableLiveData<ShowListViewState> = MutableLiveData()
        compositeDisposable.add(
            repository.getShows(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                    { response -> data.value = stateMapper.mapFromRemote(response) },
                    { data.value = ErrorEx(it.message, null) },
                    { /** Verify we got a success response **/ },
                    { data.value = Loading })
        )
        return data
    }

    fun clear() {
        totalPages = 0
        nextPage = 0
        compositeDisposable.clear()
    }

    fun loadNextPage() {
        if (nextPage < totalPages && !loadingData) {
            loadingData = true
            compositeDisposable.add(
                repository.getShows(nextPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .subscribe(
                        { response -> shows.value = stateMapper.mapFromRemote(response) },
                        { shows.value = ErrorEx(it.message, null) },
                        { /** Verify we got a success response **/ },
                        { shows.value = Loading })
            )
        }
    }
}