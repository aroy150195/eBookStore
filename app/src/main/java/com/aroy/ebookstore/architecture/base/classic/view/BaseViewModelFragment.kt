package com.aroy.ebookstore.architecture.base.classic.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Amit Roy on Date : 04/12/25
 *
 * A base fragment class that binds a [BaseViewModel] to the fragment lifecycle.
 *
 * This abstract class provides a common foundation for fragments that rely on
 * a ViewModel instance. It ensures that the ViewModel is strongly typed and
 * can be accessed safely within the fragment.
 *
 * @param T the type of [BaseViewModel] associated with this fragment.
 *          The ViewModel is expected to expose state and handle events
 *          specific to the fragment's UI.
 *
 * Usage:
 * ```
 * class LoginFragment : BaseViewModelFragment<LoginViewModel>() {
 *     // Fragment logic here
 * }
 * ```
 */
abstract class BaseViewModelFragment<T: BaseViewModel<*, *>> : BaseFragment() {

    protected abstract val viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    // Handle state updates
                }
            }
        }
    }
}