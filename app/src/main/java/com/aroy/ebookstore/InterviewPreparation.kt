package com.aroy.ebookstore

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 13/12/25
 */
fun main() = runBlocking <Unit>{
    /************** Channel Example *************/

    val channel = Channel<Int>()

    // Producer
    launch {
        for(i in 1..3) {
            channel.send(i)
            delay(500)
        }
        channel.close()
    }

    //Consumer
    for(value in channel) {
        println("Received: $value")
    }

    /************** Flow Example ****************/

    println("Starting flow collection...")

    // 1. Create a flow using the flow builder
    val numberFlow = flow {
        for(i in 1..3) {
            delay(300)
            emit(i)
        }
    }

    // 2. Collect values from the flow
    numberFlow.collect { value ->
        println("CollectedX : $value")
    }

    numberFlow.collect { value ->
        println("CollectedY : $value")
    }

    /************ MutableStateFlow ****************/
    val stateFlow = MutableStateFlow(0)

    //Collector 1
    launch {
        stateFlow.collect { value ->
            println("Collector 1 Received : $value")
        }
    }

    //Update State
    stateFlow.value = 1
    stateFlow.value = 2

    //Collector 2
    launch {
        delay(1000)
        stateFlow.collect { value ->
            println("Collector 2 Received : $value")
        }
    }
    delay(2000)
}