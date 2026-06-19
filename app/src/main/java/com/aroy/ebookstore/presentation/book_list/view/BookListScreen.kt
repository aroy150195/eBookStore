package com.aroy.ebookstore.presentation.book_list.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Aod
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aroy.ebookstore.core.SharePrefsHelper
import com.aroy.ebookstore.core.components.ClickType
import com.aroy.ebookstore.core.components.CustomTopAppBar
import com.aroy.ebookstore.core.components.ScreenType
import com.aroy.ebookstore.core.navigation.Screen
import com.aroy.ebookstore.presentation.book_list.component.BookItem
import com.aroy.ebookstore.presentation.book_list.viewModel.BookListEvent
import com.aroy.ebookstore.presentation.book_list.viewModel.BookListIntent
import com.aroy.ebookstore.presentation.book_list.viewModel.BookListViewModel
import kotlinx.coroutines.launch

/**
 * Created by Amit Roy on Date : 27/11/25
 */
@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var drawerSelectedItem by remember { mutableIntStateOf(-1) }
    var bottomBarSelectedItem by remember { mutableIntStateOf(0) }
    val sharePrefsHelper = remember { SharePrefsHelper(navController.context) }

    val drawerItems = listOf(
        "Kotlin" to Icons.Default.Code,
        "Java" to Icons.Default.Api,
        "Python" to Icons.Default.Aod,
        "Android" to Icons.Default.Android,
        "SwiftUI" to Icons.Default.Adb,
        "iOS" to Icons.Default.AcUnit,
        "Favorite" to Icons.Default.Favorite
    )
    val bottomBarItems = listOf(
        "Home" to Icons.Default.Home,
        "Settings" to Icons.Default.Settings,
        "Favorites" to Icons.Default.Favorite,
        "About" to Icons.Default.Info,
        "Profile" to Icons.Default.Person,
    )


    // Trigger LoadBooks intent automatically when the screen is first composed
    LaunchedEffect(Unit) {
        val savedBookQuery = sharePrefsHelper.getString("book_query")
        val query = savedBookQuery.ifEmpty {
            drawerSelectedItem = 0
            sharePrefsHelper.saveString("book_query", "Kotlin")
            "Kotlin"
        }
        viewModel.processIntent(BookListIntent.LoadBooks(query = query))
    }

    // Handle one-time events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is BookListEvent.NavigateToDetails -> {
                    navController.navigate(Screen.BookDetailsScreen.route)
                }

                is BookListEvent.ShowToast -> {
                    //Toast.makeText(navController.context, event.message, Toast.LENGTH_SHORT).show()
                }

                is BookListEvent.NavigateToFavorite -> {
                    // Navigate to favorite screen
                    navController.navigate(Screen.FavoriteBooksScreen.route)
                }
            }
        }
    }

    /**
     * Navigation Drawer types in Jetpack Compose Material3:
     *
     * 1. DismissibleNavigationDrawer : Pushes the screen content aside with a sliding animation when opened.
     * 2. ModalNavigationDrawer      : Overlays the screen content with a drawer that slides in from the side.
     * 3. PermanentNavigationDrawer  : Keeps the drawer always visible, shifting the main content permanently.
     *
     * DrawerSheet variants:
     * - DismissibleDrawerSheet : Container for drawer content inside a DismissibleNavigationDrawer.
     * - ModalDrawerSheet       : Container for drawer content inside a ModalNavigationDrawer.
     * - PermanentDrawerSheet   : Container for drawer content inside a PermanentNavigationDrawer.
     */
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerShape = RectangleShape
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Book Shelf",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()
                    // Drawer content
                    drawerItems.forEachIndexed { index, (item, icon) ->
                        val savedBookQuery = sharePrefsHelper.getString("book_query")
                        if(savedBookQuery.isNotEmpty() && item == savedBookQuery) {
                            drawerSelectedItem = index
                        }
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                                )
                            },
                            selected = drawerSelectedItem == index, // highlight last selected item
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                            onClick = {
                                if (drawerSelectedItem != index) {
                                    if (item == "Favorite") {
                                        // Todo: Navigate to favorite screen
                                        Toast.makeText(
                                            navController.context,
                                            "Coming soon...",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        sharePrefsHelper.saveString("book_query", item)
                                        viewModel.processIntent(BookListIntent.LoadBooks(query = item))
                                    }
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                                drawerSelectedItem = index // update selection
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.15f
                                ), // background when selected
                                selectedTextColor = MaterialTheme.colorScheme.secondary, // text color when selected
                                unselectedContainerColor = Color.Transparent, // background when not selected
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface // text color when not selected
                            ),
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = item
                                )
                            },
                        )
                    }
                    Text(
                        text = "Version v1.0.0",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    navController = navController,
                    text = "Book List",
                    screenType = ScreenType.HOME,
                    action = { clickType ->
                        when (clickType) {
                            ClickType.BACK -> {
                                navController.navigateUp()
                            }

                            ClickType.DRAWER -> {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomBarItems.forEachIndexed { index, (item, icon) ->
                        NavigationBarItem(
                            selected = bottomBarSelectedItem == index,
                            onClick = {
                                //Todo: BottomBar Navigation
                                bottomBarSelectedItem = index
                            },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = item
                                )
                            },
                            label = {
                                Text(
                                    text = item,
                                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = MaterialTheme.typography.titleSmall.fontWeight
                                )
                            }

                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(4.dp),
                    onClick = {
                        viewModel.processIntent(BookListIntent.OpenFavoriteBooks)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    elevation = FloatingActionButtonDefaults.elevation(),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite"
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator()
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                        ) {
                            items(items = state.books, key = {it.id.orEmpty()}) { bookItem ->
                                BookItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            /*navController.currentBackStackEntry?.savedStateHandle?.set(
                                                key = "book",
                                                value = bookItem
                                            )*/
                                            viewModel.processIntent(
                                                BookListIntent.SelectBook(bookItem.id)
                                            )
                                        },
                                    booksItems = bookItem
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}