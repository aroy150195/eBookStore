package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 *
 * Demonstrates usage of [MutableSharedFlow] with multiple consumers and a producer.
 *
 * In this example:
 * - A [MutableSharedFlow] of integers is created.
 * - Consumer 1 starts collecting immediately and receives all emitted values.
 * - Producer emits values 1, 2, and 3 into the flow.
 * - Consumer 2 starts collecting after a delay, so it only receives future emissions.
 *
 * Expected output:
 * ```
 * Consumer 1 Received : 1
 * Consumer 1 Received : 2
 * Consumer 1 Received : 3
 * ```
 *
 * Note:
 * - By default, [MutableSharedFlow] has no replay buffer, so late collectors
 *   (like Consumer 2) do not receive past values.
 * - To allow late collectors to receive the last emitted value(s), configure
 *   the flow with `MutableSharedFlow(replay = 1)` or higher.
 */
fun main() = runBlocking<Unit> {
    val sharedFlow = MutableSharedFlow<Int>()

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
        for(i in 1..3) {
            sharedFlow.emit(i)
        }
    }

    /**
     * Consumer 2:
     * Starts collecting after a delay of 300ms.
     * Since [MutableSharedFlow] has no replay buffer by default,
     * this consumer does not receive the earlier values (1, 2, 3).
     */
    launch {
        delay(300)
        sharedFlow.collect { value ->
            println("Consumer 2 Received : $value")
        }
    }
}