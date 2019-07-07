package com.simplekjl.showsapp.data.remote

import com.simplekjl.showsapp.BuildConfig
import com.simplekjl.showsapp.data.model.ErrorResponse
import com.simplekjl.showsapp.data.model.NotFoundResponse
import com.simplekjl.showsapp.data.model.ResponseState
import com.simplekjl.showsapp.data.model.ShowsResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkImpl constructor(private val service: MoviesDBService) : Network {
    override fun getShows(page: Int): Single<ResponseState> {
        return Single.create { emitter ->
            service.getShows(BuildConfig.ApiKey, page).enqueue(object : Callback<ResponseState> {
                override fun onFailure(call: Call<ResponseState>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(call: Call<ResponseState>, response: Response<ResponseState>) {
                    when (response.code()) {
                        401 -> {
                            emitter.onSuccess(ErrorResponse("API KEY is requird for this action ", false, 401))
                        }
                        404 -> {
                            emitter.onSuccess(NotFoundResponse("Element not found", 404))
                        }
                        200 -> {
                            emitter.onSuccess(response.body() ?: ShowsResponse(0, emptyList(), 0, 0))
                        }
                    }
                }
            })
        }
    }

}