package com.aroy.ebookstore.architecture.base.classic.view

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Amit Roy on Date : 05/12/25
 *
 * A base activity class that provides common setup and functionality
 * for all activities in the application.
 *
 * This abstract class extends [AppCompatActivity] and serves as the
 * root activity abstraction. It can be used to centralize shared logic,
 * such as:
 * - Initializing common UI components (e.g., toolbar, status bar)
 * - Handling global lifecycle events
 * - Providing utility methods for child activities
 * - Managing dependency injection or shared ViewModels
 *
 * Subclasses should extend [BaseActivity] to inherit these behaviors
 * and override specific methods as needed for their own screens.
 *
 */
abstract class BaseActivity : AppCompatActivity() {
}