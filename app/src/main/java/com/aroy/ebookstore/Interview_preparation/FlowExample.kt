package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 */
fun main() = runBlocking<Unit> {
    println("Flow start collecting....")

    //Producer
    val producerFlow = flow {
        for (i in 1..3) {
            delay(500)
            emit(i)
        }
    }

    //Consumer 1
    launch {
        producerFlow.collect { value ->
            println("Consumer1 : $value")
        }
    }

    //Consumer 2
    launch {
        producerFlow.collect { value ->
            println("Consumer2 : $value")
        }
    }
}