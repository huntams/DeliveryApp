package com.example.common.utils.data.remote

sealed class ResultLoader<T>(
) {
    class Loading<T> : ResultLoader<T>() {

    }

    data class Success<T>(
        val value: T,
    ) : ResultLoader<T>()

    data class Failure<T>(
        val throwable: Throwable,
    ) : ResultLoader<T>()


}