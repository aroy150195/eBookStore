package com.aroy.ebookstore.architecture.base.classic.view

import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base class for creating bottom sheet dialog fragments that are backed by a [BaseViewModel].
 *
 * This abstract class extends [BaseBottomSheetDialogFragment] and provides a shared foundation
 * for bottom sheets that rely on a ViewModel to manage UI state and business logic. By
 * parameterizing the ViewModel type, subclasses can strongly type their associated ViewModel
 * and access it safely within the dialog fragment.
 *
 * Typical responsibilities of this class include:
 * - Attaching the ViewModel to the bottom sheet lifecycle
 * - Observing ViewModel state and rendering UI updates
 * - Handling events or commands exposed by the ViewModel
 * - Providing a consistent MVVM pattern for bottom sheet dialogs
 *
 * @param T the type of [BaseViewModel] associated with this bottom sheet dialog fragment.
 *          The ViewModel is expected to expose state and handle events specific to the
 *          bottom sheet's UI.
 *
 * Example usage:
 * ```
 * class OptionsBottomSheet :
 *     BaseViewModelBottomSheetDialogFragment<OptionsViewModel>() {
 *
 *     override fun onCreateView(
 *         inflater: LayoutInflater,
 *         container: ViewGroup?,
 *         savedInstanceState: Bundle?
 *     ): View? {
 *         return inflater.inflate(R.layout.bottom_sheet_options, container, false)
 *     }
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         // Observe ViewModel state here
 *     }
 * }
 * ```
 */
abstract class BaseViewModelBottomSheetDialogFragment<T: BaseViewModel<*, *>> : BaseBottomSheetDialogFragment() {
    protected abstract val viewModel: T
}