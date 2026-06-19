package com.aroy.interview_prep.Interview_preparation

/**
 * Created by Amit Roy on Date : 26/12/25
 */

/**
 *
 * Function	Object Reference	Return Value	    Best Use Case
 * let	        it	         Result of the block	Transforming values, handling nullable objects, chaining operations
 * run	       this	         Result of the block	Object configuration + returning a computed result
 * apply	   this	         The object itself	    Initializing or configuring an object
 * also	        it	         The object itself	    Performing side effects (logging, debugging, validation) while keeping object
 * with	       this	         Result of the block	Calling multiple functions on the same object without repeating its name
 */

fun main() {
    letExample()
    runExample()
    applyExample()
    alsoExample()
    withExample()
    val intArr1 = intArrayOf(1, 2, 3, 0, 0)
    val intArr2 = intArrayOf(2, 5, 6)
    val intArr = intArr1.plus(intArr2).sorted()
    intArr.forEach { println(it) }
}
/**
 * `let` is a scope function that executes the block with the object as `it`.
 * It is commonly used for null checks or transformations.
 * Returns the result of the block.
 */
fun letExample() {
    val name: String? = "Amit"
    val length = name?.let {
        println("Name is $it")
        it.length
    }
    println("Length: $length")
}

/**
 * `run` is a scope function that executes the block with the object as `this`.
 * It is useful when you want to initialize or compute a value.
 * Returns the result of the block.
 */
fun runExample() {
    val greeting = "Hello".run {
        println("Inside run: $this")
        "$this World!"
    }
    println(greeting)
    /**
     * Using run for object initialization but here user returning Unit that means its not returning object,
     * it is returning nothing.its only for this object initialization
     */
    val user = User("Amit", 30).run {
        name = "Amit Kumar Run"
        age = 34
        println("Inside run: $this")
    }
}

/**
 * `apply` is a scope function that executes the block with the object as `this`.
 * It is mainly used for object configuration or initialization.
 * Returns the object itself.
 */
fun applyExample() {
    val user = User("Amit", 30).apply {
        name = "Amit Kumar"
        age = 31
    }
    println(user)
}

/**
 * `also` is a scope function that executes the block with the object as `it`.
 * It is typically used for performing side effects (like logging or debugging).
 * Returns the object itself.
 */
fun alsoExample() {
    val numbers = mutableListOf(1, 2, 3).also {
        println("Original list: $it")
        it.add(4)
    }
    println("Updated list: $numbers")
}

/**
 * `with` is a scope function that executes the block with the object as `this`.
 * It is useful when you want to call multiple functions on the same object.
 * Returns the result of the block.
 */
fun withExample() {
    val builder = StringBuilder()
    val result = with(builder) {
        append("Hello, ")
        append("World!")
        toString()
    }
    println(result)
    val user = User("Amit", 30)

    /**
     * Using with taking as receiver and its return as result of the block as user object
     */
    val result1 = with(user) {
        name = "Amit Kumar With"
        age = 31
        this
    }

    /**
     * Using with taking as receiver and its return as result of the block as Unit
     */
    val result2 = with(user) {
        name = "Amit Kumar With 2"
        age = 31
    }
}

// Supporting data class for applyExample
data class User(var name: String, var age: Int)
