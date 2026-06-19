package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Amit Roy on Date : 16/12/25
 *
 * Demonstrates concurrent execution with two jobs and explicit joins.
 *
 * - `job1` runs fetchUser() (1000 ms) and prints result.
 * - `job2` runs fetchProfile() (1500 ms) and prints result.
 * - Both jobs start concurrently inside runBlocking.
 * - `job1.join()` and `job2.join()` suspend the parent until each job completes.
 * - Total runtime ≈ 1500 ms (longest delay), not 2500 ms, because jobs run in parallel.
 *
 * Expected output order:
 * Coroutine started
 * User fetched
 * User: Amit
 * Profile fetched
 * Profile: Profile of Amit
 * Coroutine finished
 */
fun main() = runBlocking {
    println("Coroutine started")

    val elapsed = measureTimeMillis {
        val job1 = launch {
            val user = fetchUserData2()
            println("User: $user")
        }

        val job2 = launch {
            val profile = fetchProfileData2("Amit")
            println("Profile: $profile")
        }
        println("Is Job1 Alive : ${job1.isActive} / Is Job2 Alive : ${job2.isActive}")
        job1.join()
        job2.join()
        println("Is Job1 Alive : ${job1.isActive} / Is Job2 Alive : ${job2.isActive}")
    }
    println("Coroutine finished in $elapsed ms")
}

private suspend fun fetchUserData2(): String {
    delay(1000)
    return "Amit"
}

private suspend fun fetchProfileData2(user: String): String {
    delay(1500)
    return "Profile of $user"
}