package com.simplekjl.showsapp.data

import com.simplekjl.showsapp.data.model.ResponseState
import com.simplekjl.showsapp.data.remote.Network
import io.reactivex.Observable

/**
 *  Repository decides where to take data, it can be Database, Cache or Network
 */

class RepositoryImpl constructor(val network: Network) : Repository {

    override fun getShows(page: Int): Observable<ResponseState> {
        return network.getShows(page).toObservable()
    }
}