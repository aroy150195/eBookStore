package com.aroy.ebookstore.architecture.common

/**
 * Created by Amit Roy on Date : 28/11/25
 *
 * A generic sealed class that represents the state of a result
 * in asynchronous operations such as network requests or database queries.
 *
 * It provides a unified way to handle loading, success, and error states
 * across the application.
 *
 * @param T The type of data associated with a successful result.
 */
sealed class ResultState<out T> {

    /**
     * Represents an ongoing operation.
     * Typically used to show a loading indicator in the UI.
     */
    data object Loading : ResultState<Nothing>()

    /**
     * Represents a successful operation with the resulting data.
     *
     * @param data The data returned from the operation.
     */
    data class Success<out T>(val data: T) : ResultState<T>()

    /**
     * Represents a failed operation.
     *
     * @param message A human-readable error message, if available.
     * @param throwable The underlying exception that caused the failure, if available.
     */
    data class Error(val message: String?, val throwable: Throwable? = null) :
        ResultState<Nothing>()
}