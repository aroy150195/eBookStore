package com.aroy.ebookstore.architecture.base.classic.view

import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base activity class that integrates a [BaseViewModel] with the Android
 * activity lifecycle.
 *
 * This abstract class extends [BaseActivity] and provides a common foundation
 * for activities that rely on a ViewModel to manage UI state and business logic.
 * By parameterizing the ViewModel type, subclasses can strongly type their
 * associated ViewModel and access it safely.
 *
 * Typical responsibilities of this class include:
 * - Attaching the ViewModel to the activity lifecycle
 * - Observing ViewModel state and rendering UI updates
 * - Handling events or commands exposed by the ViewModel
 * - Providing a consistent pattern for MVVM architecture across activities
 *
 * @param T the type of [BaseViewModel] associated with this activity.
 *          The ViewModel is expected to expose state and handle events
 *          specific to the activity's screen.
 *
 * Example usage:
 * ```
 * class LoginActivity : BaseViewModelActivity<LoginViewModel>() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         setContentView(R.layout.activity_login)
 *         // Observe ViewModel state here
 *     }
 * }
 * ```
 */
abstract class BaseViewModelActivity<T: BaseViewModel<*, *>> : BaseActivity() {
    protected abstract val viewModel: T
}