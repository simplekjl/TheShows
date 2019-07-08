package com.simplekjl.showsapp.domain

import com.simplekjl.showsapp.data.model.ResponseType
import com.simplekjl.showsapp.data.remote.Network
import io.reactivex.Observable

/**
 *  Repository decides where to take data, it can be Database, Cache or Network
 */

class RepositoryImpl constructor(private val network: Network) : Repository {

    override fun getShows(page: Int): Observable<ResponseType> {
        return network.getShows(page).toObservable()
    }
}