package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 */
/**
 * Demonstrates usage of [MutableSharedFlow] with replay buffer.
 *
 * In this example:
 * - A [MutableSharedFlow] of integers is created with `replay = 1`.
 * - Consumer 1 starts collecting immediately and receives all emitted values.
 * - Producer emits values 1, 2, and 3 into the flow.
 * - Consumer 2 starts collecting after a delay, but because replay = 1,
 *   it immediately receives the last emitted value (3) before continuing
 *   with any future emissions.
 *
 * Expected output:
 * ```
 * Consumer 1 Received : 1
 * Consumer 1 Received : 2
 * Consumer 1 Received : 3
 * Consumer 2 Received : 3
 * ```
 *
 * Note:
 * - With `replay = 1`, late collectors always get the most recent emission.
 * - Increasing replay (e.g., `replay = 2`) would let late collectors
 *   receive more past values.
 */
fun main() = runBlocking {
    // SharedFlow with replay buffer of size 1
    val sharedFlow = MutableSharedFlow<Int>(replay = 1)

    /**
     * Consumer 1:
     * Starts collecting immediately and receives all values emitted by the producer.
     */
    launch {
        sharedFlow.collect { value ->
            println("Consumer 1 Received : $value")
        }
    }

    /**
     * Producer:
     * Emits three integer values (1, 2, 3) into the shared flow.
     */
    launch {
        for (i in 1..3) {
            sharedFlow.emit(i)
        }
    }

    /**
     * Consumer 2:
     * Starts collecting after a delay of 300ms.
     * Because replay = 1, it immediately receives the last emitted value (3).
     */
    launch {
        delay(300)
        sharedFlow.collect { value ->
            println("Consumer 2 Received : $value")
        }
    }

    delay(2000) // keep coroutine alive long enough to see results
}