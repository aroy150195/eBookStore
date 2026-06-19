package com.aroy.ebookstore.Interview_preparation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 01/01/26
 */
fun main() : Unit = runBlocking {
    val channel = Channel<Int>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    repeat(10) {
        delay(100)
        channel.send(it)
        println("Sender: $it")
    }
    channel.close()

    channel.consumeAsFlow().collect {
        println("Receiver 1 : $it")
    }

    /**
     * Not receiving any value since Channel is one-to-one communication only one receiver can receive from emitter
     */
    channel.consumeAsFlow().collect {
        println("Receiver 2 : $it")
    }
}