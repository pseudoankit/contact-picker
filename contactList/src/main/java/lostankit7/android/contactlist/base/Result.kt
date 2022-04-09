package lostankit7.android.contactlist.base

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    class Loading<T> : Result<T>()
    class Failure<T> : Result<T>()
}