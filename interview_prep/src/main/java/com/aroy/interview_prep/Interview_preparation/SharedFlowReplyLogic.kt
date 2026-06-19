package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 27/12/25
 *
 * Demonstrates the behavior of a **hot Flow** using [MutableSharedFlow] with a replay cache.
 *
 * ### Hot Flow basics:
 * - A hot Flow emits values continuously, regardless of whether collectors are attached.
 * - Collectors only receive values from the moment they subscribe onward.
 * - By default, late collectors miss previously emitted values.
 *
 * ### Replay parameter:
 * - The `replay` argument specifies how many of the most recent values should be cached
 *   and re-emitted to new subscribers.
 * - In this example, `replay = 4` means that when a new collector subscribes, it will
 *   immediately receive the last 4 emitted values before continuing with future emissions.
 *
 * ### Behavior in this example:
 * - The first collector subscribes immediately and receives all values from 0 to 9.
 * - The second collector subscribes after a 5000ms delay, long after all values have been emitted.
 *   - Without replay, it would receive nothing.
 *   - With `replay = 4`, it immediately receives the last 4 values (6, 7, 8, 9).
 *
 * ### Key points:
 * - Hot flows do not restart emission for each collector.
 * - The replay cache allows late subscribers to "catch up" on a configurable number of past values.
 * - This makes [MutableSharedFlow] useful for broadcasting events where new subscribers
 *   should not miss recent emissions.
 *
 * ### Summary:
 * - Cold Flow → starts emitting when collected, each collector gets its own sequence.
 * - Hot Flow → emits regardless of collectors, late collectors only see future emissions.
 * - Hot Flow with replay → late collectors also receive a buffer of past values.
 *
 * Output:
 *
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
 * Last 4 emitted value with reply = 4 configurable
 * Hot Flow 2 : 6
 * Hot Flow 2 : 7
 * Hot Flow 2 : 8
 * Hot Flow 2 : 9
 */
fun main() : Unit = runBlocking {
    val hotFlow = MutableSharedFlow<Int>(
        replay = 4
    )

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