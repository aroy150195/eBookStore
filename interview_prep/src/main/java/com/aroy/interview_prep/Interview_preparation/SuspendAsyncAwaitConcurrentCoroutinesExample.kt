package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates concurrent execution using coroutines with async/await.
 *
 * - `async` launches a coroutine that returns a Deferred<T>, allowing tasks to run in parallel.
 * - `await` suspends until the Deferred result is ready, resuming without blocking the thread.
 * - In this example, `fetchUser()` and `fetchProfile()` run concurrently, reducing total time.
 *
 * Usage:
 * ```
 * runBlocking {
 *     val userDeferred = async { fetchUserData() }
 *     val profileDeferred = async { fetchProfileData("Amit") }
 *     val user = userDeferred.await()
 *     val profile = profileDeferred.await()
 *     println("User: $user, Profile: $profile")
 * }
 * ```
 */
fun main() = runBlocking {
    println("Coroutine started")

    // Run two tasks concurrently
    val timeElapsed = measureTimeMillis {
        val userDeferred = async { fetchUserData() }
        val profileDeferred = async { fetchProfileData("Amit") }

        // Await results (suspends until each is ready)
        val user = userDeferred.await()
        val profile = profileDeferred.await()

        println("User: $user")
        println("Profile: $profile")
    }
    println("Time Elapsed : $timeElapsed")
    println("Coroutine finished")
}

private suspend fun fetchUserData(): String {
    delay(1000)
    return "Amit"
}

private suspend fun fetchProfileData(user: String): String {
    delay(1500)
    return "Profile of $user"
}