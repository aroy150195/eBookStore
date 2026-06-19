package com.aroy.ebookstore.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aroy.ebookstore.presentation.book_list.view.BookListScreen
import com.aroy.ebookstore.presentation.book_list.viewModel.BookListViewModel

/**
 * Created by Amit Roy on Date : 28/11/25
 */

@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.BookListScreen.route
    ) {
        composable(
            route = Screen.BookListScreen.route,
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            val viewModel: BookListViewModel = hiltViewModel()
            BookListScreen(navController, viewModel)
        }
    }
}