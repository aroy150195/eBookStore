package com.aroy.ebookstore.architecture.base.classic.view

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base class for creating bottom sheet dialog fragments in the application.
 *
 * This abstract class extends [BottomSheetDialogFragment] and serves as a
 * foundation for all custom bottom sheet implementations. It can be used to
 * centralize shared setup, styling, and behavior across multiple bottom sheet
 * dialogs, ensuring consistency and reducing boilerplate code.
 *
 * Typical responsibilities of this class include:
 * - Defining common UI behavior for bottom sheets
 * - Applying consistent theming or styling
 * - Providing utility methods for subclasses
 * - Managing lifecycle events specific to bottom sheet dialogs
 *
 * Subclasses should extend [BaseBottomSheetDialogFragment] to inherit these
 * behaviors and override specific methods as needed for their own use cases.
 *
 */
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {}