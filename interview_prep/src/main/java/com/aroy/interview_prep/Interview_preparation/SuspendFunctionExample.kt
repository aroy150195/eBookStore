package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 16/12/25
 */

fun main() = runBlocking {
    println("Coroutine started")

    val job = launch {
        val result = fetchData()
        println("Result: $result")
    }
    job.join()
    println("Coroutine completed")
    println("Is job Active : ${job.isActive}")
}

private suspend fun fetchData(): String {
    println("Fetching started on Thread : ${Thread.currentThread().name}")
    delay(2000) // <- suspends here for 2 seconds
    println("Fetching resumed on Thread : ${Thread.currentThread().name}")
    return "Data loaded"
}