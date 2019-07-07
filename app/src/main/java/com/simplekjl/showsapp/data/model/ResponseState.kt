package com.simplekjl.showsapp.data.model


sealed class ResponseState

/** HTTP 401 **/
data class ErrorResponse(val status_message: String, val success: Boolean, val status_code: Int) : ResponseState()

/** HTTP 404 **/
data class NotFoundResponse(val status_message: String, val status_code: Int) : ResponseState()

/** 200 response **/
data class ShowsResponse(
    val page: Int, val results: List<Show>, val total_results: Int,
    val total_pages: Int
) : ResponseState()