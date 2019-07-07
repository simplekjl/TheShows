package com.simplekjl.showsapp.data.remote

import com.simplekjl.showsapp.data.model.ResponseState
import io.reactivex.Single

interface Network {
    fun getShows(page: Int): Single<ResponseState>
}