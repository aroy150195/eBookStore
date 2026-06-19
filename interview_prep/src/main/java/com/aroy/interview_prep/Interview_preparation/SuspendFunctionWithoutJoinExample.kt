package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates launching a coroutine inside runBlocking without explicitly waiting for completion.
 *
 * Behavior:
 * - `runBlocking` creates a coroutine scope on the calling thread (here, main).
 * - A child coroutine is started with `launch { ... }` and scheduled immediately.
 * - The parent coroutine continues execution without waiting, printing "Coroutine finished".
 * - Even though the parent prints "finished" early, `runBlocking` enforces structured concurrency:
 *   it will not exit until all child coroutines complete.
 * - This ensures "Task done" is printed before the program terminates, but the order of logs
 *   shows the parent finishing before the child.
 *
 * Parent thread role:
 * - The parent thread executes sequentially until the end of runBlocking.
 * - Child coroutines run concurrently but share the same thread unless another dispatcher is used.
 * - The parent does not suspend for the child here, so logs interleave.
 *
 * Expected output order:
 * Coroutine started
 * Coroutine finished
 * Task done
 */
fun main() = runBlocking {
    println("Coroutine started")

    launch {
        delay(1000)
        println("Task done")
    }

    println("Coroutine finished")
}