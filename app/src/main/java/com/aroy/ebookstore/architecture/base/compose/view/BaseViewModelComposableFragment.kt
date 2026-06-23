package com.aroy.ebookstore.arch_templete.compose.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.aroy.ebookstore.architecture.base.classic.view.BaseViewModelFragment
import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel
import com.aroy.ebookstore.ui.theme.EBookStoreTheme

/**
 * Created by Amit Roy on Date : 04/12/25
 */
abstract class BaseViewModelComposableFragment<T: BaseViewModel<*, *>> : BaseViewModelFragment<T>() {

    /**
     * Define the composable UI content for this fragment
     */
    @Composable
    abstract fun Content()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * A [ComposeView] is a special Android [View] that serves as a bridge
         * between the traditional View hierarchy and Jetpack Compose.
         *
         * It allows embedding Compose UI content inside existing XML layouts,
         * Fragments, or Activities without requiring a full migration to Compose.
         * This makes it especially useful in hybrid projects where both
         * View-based and Compose-based UIs coexist.
         *
         * Typical use cases include:
         * - Gradual migration from XML layouts to Compose
         * - Hosting Compose UI inside a Fragment or Activity that still uses Views
         * - Mixing Compose components with legacy Views (e.g., MapView, WebView)
         *
         * Example usage in a Fragment:
         * ```
         * class ExampleFragment : Fragment(R.layout.fragment_example) {
         *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         *         val composeView = view.findViewById<ComposeView>(R.id.composeView)
         *         composeView.setContent {
         *             MyComposeScreen()
         *         }
         *     }
         * }
         * ```
         *
         * Note: [ComposeView] should be used when you need interoperability
         * between Compose and the View system. In a fully Compose-based project,
         * you typically don’t need it, as Compose replaces the View hierarchy entirely.
         */
        return ComposeView(requireContext()).apply {
            setContent {
                EBookStoreTheme {
                    Content()
                }
            }
        }
    }
}