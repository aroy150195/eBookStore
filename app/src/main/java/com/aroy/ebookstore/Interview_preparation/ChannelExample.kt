package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
        for(i in 1..3) {
            channel.send(i)
            delay(500)
        }
        channel.close()
    }

    /**
     * Consumer loop:
     * Iterates over the channel and prints each received value.
     * Terminates automatically when the channel is closed.
     *
     * Note: Channels do not require `collect`. This loop is the idiomatic way
     * to consume values from a channel.
     */
    for(value in channel) {
        println("Received: $value")
    }
}