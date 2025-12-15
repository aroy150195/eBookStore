package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates sequential coroutine execution with job and join().
 *
 * - A child coroutine is launched with `launch`.
 * - Inside the child, suspend functions run sequentially:
 *   first `fetchUser()` (1000 ms), then `fetchProfile()` (1500 ms).
 * - `job.join()` suspends the parent until the child completes,
 *   ensuring deterministic ordering and sequential flow.
 * - Total runtime ≈ 2500 ms (sum of delays).
 *
 * Expected output order:
 * Coroutine started
 * User: Amit
 * Profile: Profile of Amit
 * Coroutine finished
 */
fun main() = runBlocking {
    println("Coroutine started")

    val elapsed = measureTimeMillis {
        val job = launch {
            val user = fetchUser()
            println("User: $user")

            val profile = fetchProfile(user)
            println("Profile: $profile")
        }

        job.join() // suspends parent until child finishes
    }
    println("Coroutine finished in $elapsed ms")
}

suspend fun fetchUser(): String {
    delay(1000)
    return "Amit"
}

suspend fun fetchProfile(user: String): String {
    delay(1500)
    return "Profile of $user"
}