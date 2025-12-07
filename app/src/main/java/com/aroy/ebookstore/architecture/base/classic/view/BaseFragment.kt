package com.aroy.ebookstore.architecture.base.classic.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Amit Roy on Date : 04/12/25
 *
 * A base fragment class that provides common setup and utilities
 * for all fragments in the application.
 *
 * This class can be extended to share common functionality such as:
 * - inflating layouts
 * - handling lifecycle events
 * - providing utility methods for child fragments
 *
 * It serves as the root fragment abstraction, allowing other specialized
 * fragments (such as [BaseViewModelFragment]) to build upon it.
 */
abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}