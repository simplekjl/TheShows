package com.simplekjl.showsapp.presentation.features.showlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.simplekjl.showsapp.data.RxImmediateSchedulerRule
import com.simplekjl.showsapp.data.model.ErrorResponse
import com.simplekjl.showsapp.data.model.NotFoundResponse
import com.simplekjl.showsapp.data.model.ShowsResponse
import com.simplekjl.showsapp.domain.Repository
import com.simplekjl.showsapp.presentation.features.showlist.mapper.StateMapper
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ShowListActivityViewModelTest {


    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
    @Rule
    @JvmField
    val ruleForLiveData = InstantTaskExecutorRule()

    @Mock
    lateinit var mockLiveDataObserver: Observer<Any>

    private var mockRepository: Repository = mock()


    lateinit var viewModel: ShowListActivityViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ShowListActivityViewModel(mockRepository, StateMapper())
    }


    @Test
    fun getShows() {
        val expected = ShowsResponse(1, mutableListOf(), 2, 2)
        whenever(mockRepository.getShows(1)).thenReturn(Observable.just(expected))
        mockRepository.getShows(1)
            .test()
            .assertNoErrors()
            .assertValue(expected)
            .assertComplete()
    }

    @Test
    fun loadNextPage() {
        val expected = ShowsResponse(5, mutableListOf(), 2, 2)
        whenever(mockRepository.getShows(5)).thenReturn(Observable.just(expected))
        mockRepository.getShows(5)
            .test()
            .assertNoErrors()
            .assertValue(expected)
            .assertComplete()
    }

    @Test
    fun loadNextPageError() {
        val error = Throwable()
        whenever(mockRepository.getShows(5)).thenReturn(Observable.error(error))
        mockRepository.getShows(5)
            .test()
            .assertError(error)
            .assertNotComplete()
    }

    @Test
    fun loadNextPageErrorResponse() {
        val expected = ErrorResponse("error", false, 404)
        whenever(mockRepository.getShows(5)).thenReturn(Observable.just(expected))
        mockRepository.getShows(5)
            .test()
            .assertNoErrors()
            .assertValue(expected)
            .assertComplete()
    }

    @Test
    fun loadNextPageNotFound() {
        val expected = NotFoundResponse("error", 401)
        whenever(mockRepository.getShows(5)).thenReturn(Observable.just(expected))
        mockRepository.getShows(5)
            .test()
            .assertNoErrors()
            .assertValue(expected)
            .assertComplete()
    }

}