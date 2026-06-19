package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 27/12/25
 *
 * A demonstration of a **cold Flow** in Kotlin.
 *
 * ### What is a cold Flow?
 * - A cold Flow is a stream that does not start emitting values until a collector subscribes.
 * - Each collector triggers a **new execution** of the flow builder block.
 * - This means every collector receives its own independent sequence of emissions.
 *
 * ### Behavior in this example:
 * - The flow emits integers from `0` to `9`, with a `delay(100)` between each emission.
 * - When the **first collector** subscribes immediately, it starts receiving values right away:
 *   ```
 *   Cold Flow 1 : 0
 *   Cold Flow 1 : 1
 *   ...
 *   ```
 * - After **5 seconds**, the second collector subscribes. Because the flow is cold, it does not
 *   "catch up" to past emissions. Instead, it starts its own sequence from the current point onward.
 * - As a result, the second collector begins receiving values from the moment it attaches,
 *   while the first collector continues independently.
 *
 * ### Key points:
 * - Cold flows continuously emit values **as soon as a collector attaches**.
 * - Each collector gets its own emission sequence, independent of others.
 * - In this example, the second collector (attached after 5s) collects all **successive values**
 *   starting from its own subscription time.
 *
 * ### Summary:
 * - Cold Flow = lazy, starts on collection, independent for each collector.
 * - Multiple collectors can subscribe at different times, each receiving values from that point forward.
 *
 * Output:
 * Cold Flow 1 : 0
 * Cold Flow 1 : 1
 * Cold Flow 1 : 2
 * Cold Flow 1 : 3
 * Cold Flow 1 : 4
 * Cold Flow 1 : 5
 * Cold Flow 1 : 6
 * Cold Flow 1 : 7
 * Cold Flow 1 : 8
 * Cold Flow 1 : 9
 *
 * After 5s(5000ms) delay
 * Cold Flow 2 : 0
 * Cold Flow 2 : 1
 * Cold Flow 2 : 2
 * Cold Flow 2 : 3
 * Cold Flow 2 : 4
 * Cold Flow 2 : 5
 * Cold Flow 2 : 6
 * Cold Flow 2 : 7
 * Cold Flow 2 : 8
 * Cold Flow 2 : 9
 */
fun main(): Unit = runBlocking {
    val coldFlow = flow {
        repeat(10) {
            delay(100)
            emit(it)
        }
    }

    launch {
        coldFlow.collect {
            println("Cold Flow 1 : $it")
        }
    }

    launch {
        delay(5000)
        coldFlow.collect {
            println("Cold Flow 2 : $it")
        }
    }
}