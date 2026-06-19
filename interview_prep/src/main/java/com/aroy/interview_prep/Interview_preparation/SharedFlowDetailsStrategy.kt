package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 27/12/25
 */

fun main() : Unit = runBlocking {
    //bufferOverflowSuspend()
    bufferOverflowDropLatest()
    //bufferOverflowDropOldest()
}

/**
 * Demonstrates buffer overflow handling with [BufferOverflow.SUSPEND].
 *
 * ### Configuration:
 * - `replay = 2` → keeps last 2 values for new subscribers.
 * - `extraBufferCapacity = 2` → allows 2 extra values in buffer.
 * - **Total buffer size = 4** (2 replay + 2 extra).
 * - `onBufferOverflow = SUSPEND` → suspends emitter when buffer is full.
 *
 * ### Behavior:
 * - Collector is slow (`delay(1000)`), emitter is fast (`delay(100)`).
 * - When buffer fills (4 values), `emit()` suspends until collector consumes values.
 *
 * ### Key point:
 * - No values are lost, producer slowed down to match consumer.
 * - Collector eventually consumes **all 10 values** because producer waits.
 */
private fun bufferOverflowSuspend() : Unit = runBlocking {
    val hotFlow = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    launch {
        hotFlow.collect {
            delay(5000)
            println("Hot Flow BufferOverFlow Drop Suspend Collected : $it")
        }
    }

    launch {
        repeat(10) {
            delay(100)
            hotFlow.emit(it)
            println("Hot Flow BufferOverFlow Drop Suspend Emitted : $it")
        }
    }
}

/**
 * Demonstrates buffer overflow handling with [BufferOverflow.DROP_LATEST].
 *
 * ### Configuration:
 * - `replay = 2`, `extraBufferCapacity = 2`.
 * - **Total buffer size = 4**.
 * - `onBufferOverflow = DROP_LATEST` → drops newest emission when buffer is full.
 *
 * ### Behavior:
 * - Collector is slow, emitter is fast.
 * - When buffer is full, the newest values are discarded.
 *
 * ### Key point:
 * - Collector eventually consumes **4 values** (the older buffered ones).
 * - Example: may see `0,1,2,3` depending on timing.
 */
private fun bufferOverflowDropLatest() : Unit = runBlocking {
    val hotFlow = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    launch {
        hotFlow.collect {
            delay(1000)
            println("Hot Flow BufferOverFlow Drop Latest Collected : $it")
        }
    }

    launch {
        repeat(10) {
            delay(100)
            hotFlow.emit(it)
            println("Hot Flow BufferOverFlow Drop Latest Emitted : $it")
        }
    }
}

/**
 * Demonstrates buffer overflow handling with [BufferOverflow.DROP_OLDEST].
 *
 * ### Configuration:
 * - `replay = 2`, `extraBufferCapacity = 2`.
 * - **Total buffer size = 4**.
 * - `onBufferOverflow = DROP_OLDEST` → drops oldest buffered value when buffer is full.
 *
 * ### Behavior:
 * - Collector is slow, emitter is fast.
 * - When buffer is full, oldest values are discarded to make room for new ones.
 *
 * ### Key point:
 * - Collector eventually consumes **4 values** (the latest buffered ones).
 * - Example: may see `6,7,8,9` depending on timing.
 */
private fun bufferOverflowDropOldest() : Unit = runBlocking {
    val hotFlow = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    launch {
        hotFlow.collect {
            delay(1000)
            println("Hot Flow BufferOverFlow Drop Oldest Collected : $it")
        }
    }

    launch {
        repeat(10) {
            delay(100)
            hotFlow.emit(it)
            println("Hot Flow BufferOverFlow Drop Oldest Emitted : $it")
        }
    }
}