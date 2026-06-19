package com.aroy.interview_prep.Interview_preparation

/**
 * Created by Amit Roy on Date : 24/12/25
 */
import kotlinx.coroutines.*

/**
 * Demonstrates all possible states of a Coroutine Job:
 *
 * States:
 * 1. New        → Coroutine created but not started (LAZY start).
 * 2. Active     → Coroutine running or suspended.
 * 3. Completing → Parent coroutine finished but waiting for children.
 * 4. Completed  → Coroutine finished successfully.
 * 5. Cancelling → Coroutine is being cancelled, cleanup code runs.
 * 6. Cancelled  → Coroutine fully cancelled, no further execution.
 *
 * Output will show transitions between these states.
 */
fun main() = runBlocking {
    // 1. NEW STATE (lazy start)
    val job = launch(start = CoroutineStart.LAZY) {
        println("Coroutine started → Active")
        // Child coroutine to demonstrate Completing state
        launch {
            delay(500)
            println("Child coroutine finished")
        }
        try {
            repeat(3) {
                println("Working $it")
                delay(300)
            }
        } finally {
            println("Cleanup in Cancelling state")
        }
    }

    println("Job state initially: New = ${!job.isActive && !job.isCompleted}")

    // Start coroutine → moves to Active
    job.start()
    println("Job state after start: Active = ${job.isActive}")

    delay(1000) // Let parent finish but child still running → Completing
    println("Job state during child completion: Completing (still active) = ${job.isActive}")

    // Cancel job before child finishes → Cancelling
    job.cancel()
    println("Job state after cancel(): Cancelling = ${job.isCancelled && !job.isActive}")

    delay(1000) // Wait for cancellation to propagate
    println("Job state finally: Cancelled = ${job.isCancelled && job.isCompleted}")
}
