package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates launching a coroutine inside runBlocking and explicitly waiting for completion using join().
 *
 * Behavior:
 * - `runBlocking` creates a coroutine scope on the calling thread (main).
 * - A child coroutine is started with `launch { ... }`.
 * - The parent calls `job.join()`, which suspends the parent coroutine until the child finishes.
 * - This guarantees deterministic ordering: "Task done" always prints before "Coroutine finished".
 * - Unlike `await()`, `join()` does not return a result; it only ensures completion.
 *
 * Parent thread role:
 * - The parent thread suspends at `join()` and is freed until the child completes.
 * - Once the child finishes, the parent resumes and continues execution.
 * - This explicit suspension makes the parent wait for the child, unlike the previous case.
 *
 * Expected output order:
 * Coroutine started
 * Task done
 * Coroutine finished
 */
fun main() = runBlocking {
    println("Coroutine started")

    val job = launch {
        delay(1000)
        println("Task done")
    }

    job.join()
    println("Coroutine finished")
}