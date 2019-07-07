package com.simplekjl.showsapp.domain

import com.simplekjl.showsapp.data.model.ResponseState
import io.reactivex.Observable

interface Repository {
    fun getShows(page: Int): Observable<ResponseState>
}