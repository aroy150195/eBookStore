package com.aroy.ebookstore.architecture.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Amit Roy on Date : 26/11/25
 *
 * A generic Flow-based resource helper that:
 * - Emits Loading
 * - Emits cached data (if available and not forcing refresh)
 * - Fetches remote, saves to cache, then emits fresh data
 * - Emits Error if anything fails
 *
 * @param T The type of data being fetched.
 * @param loadCache A suspend function to load cached data.
 * @param fetchRemote A suspend function to fetch data from network.
 * @param saveRemoteData A suspend function to persist the fetched data.
 */
fun <T> updateResult(
    loadCache: suspend () -> T?,
    fetchRemote: suspend () -> Either<T, ApiError>,
    saveRemoteData: suspend (T) -> Unit,
): Flow<ResultState<T>> = flow {
    emit(ResultState.Loading)
    val cache = loadCache()
    if (cache != null) {
        emit(ResultState.Success(cache))
    }
    emit(
        when (val fetchRemoteResult = fetchRemote()) {
            is Either.Success -> {
                saveRemoteData(fetchRemoteResult.value)
                ResultState.Success(fetchRemoteResult.value)
            }

            is Either.Failure -> {
                ResultState.Error(fetchRemoteResult.value.message)
            }
        }
    )
}