package com.aroy.ebookstore.architecture.base.classic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base class for creating bottom sheet dialog fragments that integrate
 * Android's [ViewDataBinding] with a [BaseViewModel].
 *
 * This abstract class extends [BaseViewModelBottomSheetDialogFragment] and
 * provides a shared foundation for bottom sheets that use the Data Binding
 * library to bind UI components directly to observable data exposed by a
 * ViewModel. It ensures that both the binding and ViewModel lifecycles are
 * properly tied to the bottom sheet dialog fragment.
 *
 * Typical responsibilities of this class include:
 * - Inflating the layout with Data Binding
 * - Exposing the binding instance to subclasses
 * - Connecting the binding to the associated ViewModel
 * - Providing lifecycle-aware observation of ViewModel state
 *
 * @param T the type of [ViewDataBinding] associated with this bottom sheet's layout.
 *          This allows subclasses to access the generated binding class directly.
 * @param VM the type of [BaseViewModel] associated with this bottom sheet dialog fragment.
 *           The ViewModel is expected to manage UI state and handle events specific
 *           to the bottom sheet's screen.
 *
 * Example usage:
 * ```
 * class OptionsBottomSheet :
 *     BaseDataBindingBottomSheetDialogFragment<BottomSheetOptionsBinding, OptionsViewModel>() {
 *
 *     override fun onCreateView(
 *         inflater: LayoutInflater,
 *         container: ViewGroup?,
 *         savedInstanceState: Bundle?
 *     ): View? {
 *         binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_options, container, false)
 *         binding?.viewModel = viewModel
 *         return binding?.root
 *     }
 * }
 * ```
 */
abstract class BaseViewDataBindingBottomSheetDialogFragment<T : ViewDataBinding, VM : BaseViewModel<*, *>> :
    BaseViewModelBottomSheetDialogFragment<VM>() {
    private var _binding: T? = null

    /**
     * Provides access to the [ViewDataBinding] instance associated with this fragment.
     *
     * This property exposes the non-nullable binding object after the fragment's
     * view has been created. It is backed by the nullable `_binding` field and
     * uses the not-null assertion (!!) to guarantee safe access once the view
     * lifecycle is active.
     *
     * Note: Accessing this property outside of the fragment's view lifecycle
     * (e.g., before `onCreateView` or after `onDestroyView`) will result in a
     * [NullPointerException]. Always use it only when the fragment's view is
     * valid.
     *
     * @see _binding the backing nullable binding field
     */
    protected val binding: T
        get() = _binding!!

    protected abstract fun bindingInflater(): Inflater<T>

    /**
     * Binds the [viewModel] instance to the layout's Data Binding variable.
     *
     * This function connects the fragment's [BaseViewModel] to the XML layout
     * by assigning it to the `viewModel` variable defined in the layout's
     * `<data>` section. The binding uses the generated [BR] class, which
     * contains identifiers for all declared variables.
     *
     * Example XML:
     * ```
     * <layout>
     *   <data>
     *     <variable
     *       name="viewModel"
     *       type="com.aroy.corearch.ui.viewmodel.LoginViewModel" />
     *   </data>
     *   ...
     * </layout>
     * ```
     *
     * By calling `binding.setVariable(BR.viewModel, viewModel)`, the layout
     * can automatically observe and react to changes in the ViewModel's
     * exposed state.
     *
     * Note: Ensure that the `viewModel` variable is declared in the layout,
     * otherwise this binding call will fail at runtime.
     */
    protected open fun bindViewModel() {
        //BR is left here ex: BR.viewmodel, BR._all etc instead of 1 should give BR value
        binding.setVariable(1, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = bindingInflater().invoke(inflater, container, false)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        bindViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}