package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 *
 * Demonstrates usage of [Channel] in Kotlin coroutines.
 *
 * In this example:
 * - A [Channel] of integers is created.
 * - A producer coroutine sends three integer values (1, 2, 3) into the channel,
 *   with a delay of 500ms between each send.
 * - After sending all values, the channel is closed to signal completion.
 * - A consumer loop iterates over the channel and prints each received value.
 *
 * Behavior:
 * - Channels provide a way for coroutines to communicate safely.
 * - Each value is delivered once to the consumer (point-to-point communication).
 * - Closing the channel ensures the consumer loop terminates gracefully.
 *
 * Important:
 * - Unlike [Flow], [StateFlow], or [SharedFlow], **channels do not use `collect`**.
 *   Values are consumed using `for (value in channel)` or `channel.receive()`.
 *
 * Expected output:
 * ```
 * Received: 1
 * Received: 2
 * Received: 3
 * ```
 *
 * Notes:
 * - Channels do not replay values. If no consumer is ready, sent values may be buffered
 *   or dropped depending on the channel configuration.
 * - Channels are best suited for producer–consumer scenarios rather than state
 *   management or event broadcasting.
 */
fun main() = runBlocking {
    val channel = Channel<Int>()

    /**
     * Producer coroutine:
     * Sends three integer values into the channel with a delay between each.
     * Closes the channel after sending all values.
     */
    launch {
        repeat(10) {
            delay(100)
            channel.send(it)
            println("Sent: $it")
        }
        channel.close()
    }

    /**
     * Demonstrates consuming values from a Channel using `consumeAsFlow`.
     *
     * Channels in Kotlin are designed for one-to-one communication:
     * - Each element sent into the channel is delivered to a single receiver.
     * - Once a consumer collects a value, it is removed from the channel buffer
     *   and cannot be collected again by another consumer.
     *
     * In this example:
     * - Receiver1 collects and prints all values from the channel.
     * - Receiver2 also attempts to collect, but will not receive any values
     *   because Receiver1 has already consumed them.
     *
     * For broadcasting to multiple collectors, consider using `SharedFlow` or `StateFlow`,
     * which allow multiple subscribers to observe the same stream of values.
     */
    channel.consumeAsFlow().collect {
        println("Receiver 1: $it")
    }

    channel.consumeAsFlow().collect {
        println("Receiver 2: $it")
    }
}