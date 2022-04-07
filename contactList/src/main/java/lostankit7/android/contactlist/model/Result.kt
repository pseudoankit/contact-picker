package lostankit7.android.contactlist.model

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    class Loading<T>() : Result<T>()
    data class Failure<T>(val message: String) : Result<T>()
}