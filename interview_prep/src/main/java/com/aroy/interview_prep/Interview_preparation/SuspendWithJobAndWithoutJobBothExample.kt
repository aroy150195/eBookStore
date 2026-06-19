package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates mixed coroutine execution: one launched as a Job and another called directly.
 *
 * Flow:
 * - A child coroutine (`job`) is launched to run fetchUserData4() (1000 ms delay).
 *   This runs concurrently and prints "User: Amit" after completion.
 * - Meanwhile, the parent coroutine directly calls fetchProfileData4("Amit") (1500 ms delay).
 *   Because it's a direct suspend call, the parent suspends until it completes,
 *   then prints "Profile: Profile of Amit".
 * - After the profile call, `job.join()` ensures the child coroutine has finished.
 *   If the job is already complete, join returns immediately.
 * - `measureTimeMillis` captures total elapsed time for the block.
 *
 * Parent thread role:
 * - The parent coroutine is suspended during fetchProfileData4().
 * - The child coroutine runs in parallel, overlapping with the parent’s suspension.
 * - Total runtime ≈ longest delay (1500 ms), not the sum.
 *
 * Expected output order:
 * Coroutine started
 * User: Amit
 * Profile: Profile of Amit
 * Coroutine finished in ~1500 ms
 */
fun main() = runBlocking {
    println("Coroutine started")

    val elapsed = measureTimeMillis {
        val job = launch {
            val user = fetchUserData4()
            println("User: $user")
        }

        val profile = fetchProfileData4("Amit")
        println("Profile: $profile")

        job.join()
    }
    println("Coroutine finished in $elapsed ms")
}

private suspend fun fetchUserData4(): String {
    delay(1000)
    return "Amit"
}

private suspend fun fetchProfileData4(user: String): String {
    delay(1500)
    return "Profile of $user"
}