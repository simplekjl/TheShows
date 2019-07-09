package com.simplekjl.showsapp.presentation.features.showlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.simplekjl.showsapp.data.model.Show
import com.simplekjl.showsapp.domain.Repository
import com.simplekjl.showsapp.presentation.features.showlist.mapper.StateMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowListActivityViewModel constructor(private val repository: Repository, private val stateMapper: StateMapper) :
    ViewModel() {
    private var compositeDisposable = CompositeDisposable()
    // special case since we want pagination
    val shows: MutableLiveData<PagedList<Show>> = MutableLiveData()

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
        compositeDisposable.clear()
    }
}