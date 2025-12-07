package com.aroy.ebookstore.core.repository

import com.aroy.ebookstore.architecture.common.ApiError
import com.aroy.ebookstore.architecture.common.Either
import com.aroy.ebookstore.core.network.service.BooksAPI
import com.aroy.ebookstore.model.BookResponse
import javax.inject.Inject

/**
 * Created by Amit Roy on Date : 29/11/25
 */
class BookBuilder @Inject constructor(
    private val api: BooksAPI
) {
    suspend fun build(
        query: String,
        author: String? = null,
    ): Either<BookResponse, ApiError> =
        try {
            val response = api.searchBooks(query, author)
            when {
                response.items.isNullOrEmpty() -> Either.Failure(ApiError("No books found"))
                else -> Either.Success(response)
            }
        } catch (e: Exception) {
            Either.Failure(ApiError(e.message ?: "Unknown error"))
        }
}