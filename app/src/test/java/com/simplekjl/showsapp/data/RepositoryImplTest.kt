package com.simplekjl.showsapp.data

import com.nhaarman.mockitokotlin2.whenever
import com.simplekjl.showsapp.data.model.ErrorResponse
import com.simplekjl.showsapp.data.model.NotFoundResponse
import com.simplekjl.showsapp.data.model.Show
import com.simplekjl.showsapp.data.model.ShowsResponse
import com.simplekjl.showsapp.data.remote.Network
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RepositoryImplTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var mockNetwork: Network


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getShows() {
        val showList = listOf(
            Show(
                "", "0.0".toFloat(), 0, "", "0.0".toFloat(),
                "", "", ""
            )
        )
        val showsResponse = ShowsResponse(0, showList, 12, 0)
        whenever(mockNetwork.getShows(1)).thenReturn(Single.create { it.onSuccess(showsResponse) })
        mockNetwork.getShows(1)
            .test()
            .assertNoErrors()
            .assertValue(showsResponse)
            .assertComplete()
    }

    @Test
    fun getShowsNotFoundResponse() {
        val notFoundResponse = NotFoundResponse("", 404)

        whenever(mockNetwork.getShows(1)).thenReturn(Single.create { it.onSuccess(notFoundResponse) })
        mockNetwork.getShows(1)
            .test()
            .assertNoErrors()
            .assertValue(notFoundResponse)
            .assertComplete()
    }

    @Test
    fun getShowsErrorResponse() {
        val errorResponse = ErrorResponse("Api required", false, 401)

        whenever(mockNetwork.getShows(1)).thenReturn(Single.create { it.onSuccess(errorResponse) })
        mockNetwork.getShows(1)
            .test()
            .assertNoErrors()
            .assertValue(errorResponse)
            .assertComplete()
    }
}