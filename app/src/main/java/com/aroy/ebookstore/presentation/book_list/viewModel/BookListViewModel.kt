package com.aroy.ebookstore.presentation.book_list.viewModel

import androidx.lifecycle.viewModelScope
import com.aroy.ebookstore.architecture.common.ResultState
import com.aroy.ebookstore.architecture.base.compose.viewmodel.BaseComposeViewModel
import com.aroy.ebookstore.core.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Amit Roy on Date : 27/11/25
 */
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BookRepository
) : BaseComposeViewModel<BookListState, BookListEvent, BookListIntent>(BookListState()) {

    override suspend fun handleIntent(intent: BookListIntent) {
        when (intent) {
            is BookListIntent.LoadBooks -> loadBooks(intent.query, intent.author)
            is BookListIntent.SelectBook -> {
                sendEvent(BookListEvent.NavigateToDetails(intent.bookId))
            }
            is BookListIntent.OpenFavoriteBooks -> {
                sendEvent(BookListEvent.NavigateToFavorite)
            }
        }
    }

    fun loadBooks(query: String, author: String? = null) = viewModelScope.launch {
        repository.searchBooks(query, author).collect { result ->
            when (result) {
                is ResultState.Loading -> setState { copy(isLoading = true) }
                is ResultState.Success -> {
                    setState {
                        copy(
                            isLoading = false,
                            books = result.data.items ?: emptyList()
                        )
                    }
                }
                is ResultState.Error -> {
                    setState {
                        copy(isLoading = false, error = result.message)
                    }
                    sendEvent(BookListEvent.ShowToast(result.message))
                }
            }
        }
    }
}