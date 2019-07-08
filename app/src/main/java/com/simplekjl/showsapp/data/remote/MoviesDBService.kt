package com.simplekjl.showsapp.data.remote

import com.simplekjl.showsapp.data.model.ShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * for more information {@url https://developers.themoviedb.org/3/tv/get-popular-tv-shows}
 */
interface MoviesDBService {

    @GET("popular")
    fun getShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<ShowsResponse>
}