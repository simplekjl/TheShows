package com.simplekjl.showsapp.presentation.features.showlist.mapper

import com.simplekjl.showsapp.data.model.ErrorResponse
import com.simplekjl.showsapp.data.model.NotFoundResponse
import com.simplekjl.showsapp.data.model.ResponseType
import com.simplekjl.showsapp.data.model.ShowsResponse
import com.simplekjl.showsapp.presentation.features.showlist.ErrorMessage
import com.simplekjl.showsapp.presentation.features.showlist.NotFoundMessage
import com.simplekjl.showsapp.presentation.features.showlist.ShowListViewState
import com.simplekjl.showsapp.presentation.features.showlist.Success


open class StateMapper : EntityMapper<ResponseType, ShowListViewState> {
    override fun mapFromRemote(type: ResponseType): ShowListViewState {
        return when (type) {
            is ErrorResponse -> {
                ErrorMessage(type.status_message, type.success, type.status_code)
            }
            is NotFoundResponse -> {
                NotFoundMessage(type.status_message, type.status_code)
            }
            is ShowsResponse -> {
                Success(type.page, type.results, type.total_results, type.total_pages)
            }
        }
    }
}