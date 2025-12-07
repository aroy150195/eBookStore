package com.aroy.ebookstore.architecture.base.classic.view

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base class for creating dialog fragments in the application.
 *
 * This abstract class extends [AppCompatDialogFragment] and serves as a
 * foundation for all custom dialog fragment implementations. It can be used
 * to centralize shared setup, styling, and behavior across multiple dialogs,
 * ensuring consistency and reducing boilerplate code.
 *
 * Typical responsibilities of this class include:
 * - Applying consistent theming or styling for dialogs
 * - Defining shared lifecycle or initialization logic
 * - Providing utility methods for subclasses
 * - Serving as the root abstraction for reusable dialog components
 *
 * Subclasses should extend [BaseDialogFragment] to inherit these behaviors
 * and override specific methods as needed for their own use cases.
 *
 * Example usage:
 * ```
 * class ConfirmationDialog : BaseDialogFragment() {
 *     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
 *         return AlertDialog.Builder(requireContext())
 *             .setTitle("Confirm Action")
 *             .setMessage("Are you sure you want to proceed?")
 *             .setPositiveButton("Yes") { _, _ -> /* handle confirm */ }
 *             .setNegativeButton("No", null)
 *             .create()
 *     }
 * }
 * ```
 */
abstract class BaseDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val window = dialog.window
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        if(dialog != null && dialog?.window != null) {
            val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
            params?.let { p ->
                p.width = ViewGroup.LayoutParams.MATCH_PARENT
                p.height = ViewGroup.LayoutParams.WRAP_CONTENT
                dialog?.window?.attributes = p as WindowManager.LayoutParams?
            }
        }
        super.onStart()
    }
}