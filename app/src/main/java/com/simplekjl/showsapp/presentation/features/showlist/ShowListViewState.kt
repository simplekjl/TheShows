package com.simplekjl.showsapp.presentation.features.showlist

import com.simplekjl.showsapp.data.model.Show

sealed class ShowListViewState

object Loading : ShowListViewState()
data class Success(
    val page: Int, val results: List<Show>, val total_results: Int,
    val total_pages: Int
) : ShowListViewState()

data class ErrorMessage(val status_message: String, val success: Boolean, val status_code: Int) : ShowListViewState()
data class NotFoundMessage(val status_message: String, val status_code: Int) : ShowListViewState()
data class ErrorEx(val msg: String?, val code: Int?) : ShowListViewState()