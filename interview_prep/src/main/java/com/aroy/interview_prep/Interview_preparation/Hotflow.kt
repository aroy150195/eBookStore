package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 27/12/25
 *
 * Demonstrates the behavior of a **hot Flow** using [MutableSharedFlow].
 *
 * ### Hot Flow characteristics:
 * - A hot Flow emits values continuously, regardless of whether collectors are attached.
 * - Collectors only receive values **from the moment they subscribe onward**.
 * - Unlike cold flows, hot flows do not restart the emission sequence for each collector.
 *
 * ### Behavior in this example:
 * - The first collector subscribes immediately and receives all emitted values:
 *   ```
 *   Hot Flow 1 : 0
 *   Hot Flow 1 : 1
 *   ...
 *   ```
 * - The second collector subscribes after a delay:
 *   - If the delay is **5000ms**, the collector attaches after all values have already been emitted,
 *     so it receives nothing.
 *   - If the delay is **500ms**, the collector attaches while emission is still ongoing,
 *     so it collects the remaining values (e.g., 5 through 9).
 *
 * ### Key points:
 * - Hot flows do not replay past emissions unless explicitly configured with a replay cache.
 * - Timing of collector subscription determines which values are observed.
 * - Use `replay` in [MutableSharedFlow] if you want late subscribers to also receive previously emitted values.
 *
 * ### Summary:
 * - Cold Flow → starts emitting when collected, each collector gets its own sequence.
 * - Hot Flow → emits regardless of collectors, late collectors only see future emissions.
 *
 * Output :
 * If time = 5000ms
 * Collector 1 will receives all emitted values and Collector 2 will not receives any values as within that delay
 * all values already emitted hence late collector will not receives any value
 * Hot Flow 1 : 0
 * Hot Flow 1 : 1
 * Hot Flow 1 : 2
 * Hot Flow 1 : 3
 * Hot Flow 1 : 4
 * Hot Flow 1 : 5
 * Hot Flow 1 : 6
 * Hot Flow 1 : 7
 * Hot Flow 1 : 8
 * Hot Flow 1 : 9
 *
 * If time = 500ms
 * Collector 1 will receives all emitted values and Collector 2 will receives few values as within that delay
 * few values still emitting hence late collector will receives few values only
 * Hot Flow 1 : 0
 * Hot Flow 1 : 1
 * Hot Flow 1 : 2
 * Hot Flow 1 : 3
 * Hot Flow 1 : 4
 * Hot Flow 2 : 4
 * Hot Flow 1 : 5
 * Hot Flow 2 : 5
 * Hot Flow 1 : 6
 * Hot Flow 2 : 6
 * Hot Flow 1 : 7
 * Hot Flow 2 : 7
 * Hot Flow 1 : 8
 * Hot Flow 2 : 8
 * Hot Flow 1 : 9
 * Hot Flow 2 : 9
 */

fun main(): Unit = runBlocking {
    val hotFlow = MutableSharedFlow<Int>()

    launch {
        hotFlow.collect {
            println("Hot Flow 1 : $it")
        }
    }

    launch {
        repeat(10) {
            delay(100)
            hotFlow.emit(it)
        }
    }

    launch {
        delay(5000)
        hotFlow.collect {
            println("Hot Flow 2 : $it")
        }
    }
}