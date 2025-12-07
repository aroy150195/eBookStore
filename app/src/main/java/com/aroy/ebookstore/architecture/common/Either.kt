package com.aroy.ebookstore.architecture.common

/**
 * Created by Amit Roy on Date : 29/11/25
 */
sealed class Either<out Success, out Failure> {
    data class Success<out Success>(val value: Success) : Either<Success, Nothing>()
    data class Failure<out Failure>(val value: Failure) : Either<Nothing, Failure>()
}