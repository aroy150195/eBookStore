package com.aroy.interview_prep.Interview_preparation

/**
 * Created by Amit Roy on Date : 19/02/26
 */
fun main() {
    /**
     * Inlined function call
     */
    /*messageTest {
        println("This is first message")
        return
    }
    messageTest {
        println("This is second message")
    }*/

    /**
     * Cross Inlined function call
     */
    /*messageTest {
        println("This is first message")
        return@messageTest
    }
    messageTest {
        println("This is second message")
    }*/
    /**
     * No Inlined function call
     */
    messageTest {
        println("This is first message")
        return@messageTest
    }
    messageTest {
        println("This is second message")
    }
}

/**
 * Inlined function
 *
 */
/*inline fun messageTest(a: () -> Unit) {
    a.invoke()
}*/

/**
 * Cross Inlined function
 *
 */
/*inline fun messageTest(crossinline a: () -> Unit) {
    a.invoke()
}*/

/**
 * No Inlined function
 *
 */
inline fun messageTest(noinline a: () -> Unit) {
    a.invoke()
}