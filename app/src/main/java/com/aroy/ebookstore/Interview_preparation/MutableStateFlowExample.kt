package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 *
 * Demonstrates usage of [MutableStateFlow] with multiple collectors.
 *
 * In this example:
 * - A [MutableStateFlow] is created with an initial value of 0.
 * - Collector 1 starts immediately and begins collecting values.
 * - The state is updated twice (`1` then `2`).
 * - Collector 2 starts after a delay of 1000ms.
 *
 * Behavior:
 * - Collector 1 only prints the latest value (`2`) because [MutableStateFlow]
 *   conflates updates: intermediate values may be skipped if the collector
 *   cannot keep up.
 * - Collector 2 immediately receives the current state (`2`) when it starts,
 *   since [MutableStateFlow] always holds the latest value.
 *
 * Expected output:
 * ```
 * Collector 1 Received : 2
 * Collector 2 Received : 2
 * ```
 *
 * Notes:
 * - Unlike [MutableSharedFlow], [MutableStateFlow] always has a current value.
 * - Late collectors always receive the most recent value instantly.
 * - If you want to observe *all* intermediate updates, you need to ensure
 *   collectors process emissions without delay, or use [MutableSharedFlow].
 */
fun main() = runBlocking {
    val stateFlow = MutableStateFlow(0)

    /**
     * Collector 1:
     * Starts collecting immediately. Because updates are fast,
     * it only prints the latest value (2).
     */
    launch {
        stateFlow.collect { value ->
            println("Collector 1 Received : $value")
        }
    }

    /**
     * Update state:
     * Sets the value to 1, then to 2.
     * Only the latest value (2) is observed by collectors.
     */
    stateFlow.value = 1
    stateFlow.value = 2

    /**
     * Collector 2:
     * Starts collecting after a delay of 1000ms.
     * Immediately receives the current state (2).
     */
    launch {
        delay(1000)
        stateFlow.collect { value ->
            println("Collector 2 Received : $value")
        }
    }
    delay(2000)
}