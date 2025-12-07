package com.aroy.ebookstore.architecture.base.classic.view

import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base class for creating dialog fragments that are backed by a [BaseViewModel].
 *
 * This abstract class extends [BaseDialogFragment] and provides a common foundation
 * for dialog fragments that rely on a ViewModel to manage UI state and business logic.
 * By parameterizing the ViewModel type, subclasses can strongly type their associated
 * ViewModel and access it safely within the dialog fragment.
 *
 * Typical responsibilities of this class include:
 * - Attaching the ViewModel to the dialog fragment lifecycle
 * - Observing ViewModel state and rendering UI updates
 * - Handling events or commands exposed by the ViewModel
 * - Providing a consistent MVVM pattern for dialog fragments
 *
 * @param T the type of [BaseViewModel] associated with this dialog fragment.
 *          The ViewModel is expected to expose state and handle events specific
 *          to the dialog's UI.
 *
 * @property viewModel The abstract ViewModel instance that subclasses must provide.
 *                     It is used to bind UI state and handle user interactions
 *                     within the dialog fragment.
 *
 * Example usage:
 * ```
 * class ConfirmationDialog :
 *     BaseViewModelDialogFragment<ConfirmationViewModel>() {
 *
 *     override val viewModel: ConfirmationViewModel by viewModels()
 *
 *     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
 *         return AlertDialog.Builder(requireContext())
 *             .setTitle("Confirm Action")
 *             .setMessage("Are you sure you want to proceed?")
 *             .setPositiveButton("Yes") { _, _ -> viewModel.onConfirm() }
 *             .setNegativeButton("No", null)
 *             .create()
 *     }
 * }
 * ```
 */
abstract class BaseViewModelDialogFragment<T: BaseViewModel<*, *>> : BaseDialogFragment(){
    protected abstract val viewModel: T
}