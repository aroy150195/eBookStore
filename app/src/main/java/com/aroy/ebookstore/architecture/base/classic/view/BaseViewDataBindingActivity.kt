package com.aroy.ebookstore.architecture.base.classic.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.aroy.ebookstore.architecture.base.classic.viewmodel.BaseViewModel

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base activity class that integrates Android's [ViewDataBinding] with a [BaseViewModel].
 *
 * This abstract class extends [BaseViewModelActivity] to provide a common foundation
 * for activities that use the Data Binding library to bind UI components directly
 * to observable data exposed by a ViewModel. It ensures that both the binding and
 * ViewModel lifecycles are properly tied to the activity.
 *
 * Typical responsibilities of this class include:
 * - Inflating the layout with Data Binding
 * - Exposing the binding instance to subclasses
 * - Connecting the binding to the associated ViewModel
 * - Providing lifecycle-aware observation of ViewModel state
 *
 * @param T the type of [ViewDataBinding] associated with this activity's layout.
 *          This allows subclasses to access the generated binding class directly.
 * @param VM the type of [BaseViewModel] associated with this activity.
 *           The ViewModel is expected to manage UI state and handle events
 *           specific to the activity's screen.
 *
 * @property binding The nullable binding instance for the activity's layout.
 *                   It is initialized when the layout is inflated and should
 *                   be cleared appropriately to avoid memory leaks.
 *
 * Example usage:
 * ```
 * class LoginActivity :
 *     BaseViewDataBindingActivity<ActivityLoginBinding, LoginViewModel>() {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
 *         binding?.viewModel = viewModel
 *     }
 * }
 * ```
 */
abstract class BaseViewDataBindingActivity<T : ViewDataBinding, VM : BaseViewModel<*, *>> :
    BaseViewModelActivity<VM>() {
    protected var _binding: T? = null

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

    @LayoutRes
    protected abstract fun layoutResId(): Int?

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

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        layoutResId()?.let {
            _binding = DataBindingUtil.setContentView(this, it)
            binding.lifecycleOwner = this
            bindViewModel()
        }
    }
}