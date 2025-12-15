package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Demonstrates sequential coroutine execution with two suspend functions.
 *
 * - `fetchUser()` suspends for 1000 ms, then returns a user.
 * - `fetchProfile()` suspends for 1500 ms, but only starts after `fetchUser()` completes.
 * - Unlike concurrent execution with async/await, here the functions run one after another.
 * - Total runtime ≈ sum of delays (1000 + 1500 = 2500 ms).
 *
 * Expected output order:
 * Coroutine started
 * User fetched
 * Profile fetched
 * User: Amit
 * Profile: Profile of Amit
 * Coroutine finished
 */
fun main() = runBlocking {
    println("Coroutine started")

    val elapsed = measureTimeMillis {
        // Sequential execution
        val user = fetchUser3()          // waits until fetchUser completes
        val profile = fetchProfile3(user) // starts only after fetchUser is done

        println("User: $user")
        println("Profile: $profile")
    }

    println("Coroutine finished in $elapsed ms")
}

suspend fun fetchUser3(): String {
    delay(1000) // simulate network call
    println("User fetched")
    return "Amit"
}

suspend fun fetchProfile3(user: String): String {
    delay(1500) // simulate another network call
    println("Profile fetched")
    return "Profile of $user"
}