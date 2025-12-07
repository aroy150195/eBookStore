package com.aroy.ebookstore.core.repository

import com.aroy.ebookstore.architecture.common.ResultState
import com.aroy.ebookstore.architecture.common.updateResult
import com.aroy.ebookstore.data.dao.BookResponseDao
import com.aroy.ebookstore.model.BookResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Amit Roy on Date : 27/11/25
 *
 * Repository layer responsible for managing book data from both
 * local (Room database) and remote (API service) sources.
 *
 * This class abstracts the data operations and provides a unified
 * Flow-based API to the ViewModel/UI layer. It leverages the
 * [updateResult] core utility to standardize emission of
 * [ResultState] values (Loading, Success, Error).
 *
 * @property bookDao The Data Access Object (DAO) for cached book responses.
 * @property bookBuilder The Builder used to fetch book data from the network.
 */
@Singleton
class BookRepository @Inject constructor(
    private val bookBuilder: BookBuilder,
    private val bookDao: BookResponseDao
) {
    /**
     * Searches for books based on the given query and optional filters.
     *
     * This function:
     * - Emits [ResultState.Loading] initially.
     * - Emits cached data from the local database if available and not forcing refresh.
     * - Fetches fresh data from the remote API if cache is missing or refresh is requested.
     * - Saves the remote data into the local database.
     * - Emits [ResultState.Success] with the resulting [BookResponse].
     * - Emits [ResultState.Error] if any exception occurs.
     *
     * @param query The search query string (e.g., book title or keyword).
     * @param author Optional author filter for narrowing results.
     * @return A [Flow] emitting [ResultState] objects representing the loading,
     *         success, or error state of the operation.
     */
    fun searchBooks(
        query: String,
        author: String? = null,
    ): Flow<ResultState<BookResponse>> = updateResult(
        loadCache = {
            bookDao.getLatestResponse()
        },
        fetchRemote = {
            bookBuilder.build(query, author)
        },
        saveRemoteData = { remote ->
            bookDao.insertResponse(bookResponse = remote.copy(id = 1))
        }
    )
}