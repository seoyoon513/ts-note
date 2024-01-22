package com.teamboring.ts_note.common

sealed class ResultState<T>(
    val data: T? = null,
    val message: String = ""
) {
    class Success<T>(data: T, message: String = "") : ResultState<T>(data, message)
    class Error<T>(data: T? = null, message: String = "Result Error") : ResultState<T>(data = data, message = message)
    class Loading<T>(data: T? = null, message: String = "") : ResultState<T>(data, message)

    class Empty<T> : ResultState<T>()
}