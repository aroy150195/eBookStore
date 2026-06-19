package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Amit Roy on Date : 24/12/25
 */
fun main() {
    println("Amit".removeFirstCharacter())
    println("1234567890123452".maskedNumber()) // Output: 1234****123***2
    stringOperation()
    println("Kolkata Mumbai Delhi Hyderabad Chennai Pune".stringOperationOptimized1())
    println("Kolkata Mumbai Delhi Hyderabad Chennai Pune".stringOperationOptimized2())
    arrayOperation()
    arrayOperationOptimized()
    reverseString("Amit Roy")
    println("Level".isPalindrome())
    println("AMit".isPalindrome())
    operatorCheck()
    println(removeDuplicateUsingDistinct(listOf(1, 5, 3, 5, 6, 2, 4, 7, 8, 7)).sorted())
    println(
        removeDuplicateUsingDistinct(
            listOf(
                "Kolkata",
                "Mumbai",
                "Delhi",
                "Kolkata"
            ).sortedBy { it.substring(0, 1) })
    )
    println(removeDuplicateUsingSet(listOf(1, 5, 3, 5, 6, 2, 4, 7, 8, 7)).sorted())
    println(
        removeDuplicateUsingSet(
            listOf(
                "Kolkata",
                "Mumbai",
                "Delhi",
                "Kolkata"
            )
        ).sortedBy { it.substring(0, 1) })
    println(groupingByEachCount("Amit Roy is a good boy"))
    filterAndMap()

    printState(Result.Loading)
    printState(Result.Success("Data fetched"))
    printState(Result.Error("Server Error"))

    printEither(Either.Success("User data fetched"))
    printEither(Either.Failure("Internal server error"))

    coroutinesAsync()
    differenceCheck()
    printNumbersOperation()
}

private fun String.removeFirstCharacter(): Char {
    return this[this.length - 1];
}

private fun String.maskedNumber(): String {
    val firstPart = substring(0, 4)
    val middleMask = "*****"
    val secondPart = substring(9, 12)
    val trailingMask = "***"
    val lastDigit = substring(length - 1)
    return firstPart + middleMask + secondPart + trailingMask + lastDigit
}

private fun stringOperation() {
    val cityNameArr = "Kolkata Mumbai Delhi".split(" ")
    val city1 =
        cityNameArr[0].reversed().replaceFirstChar { it.uppercase() }.replaceAfter('o', "k") + " "
    val city2 =
        cityNameArr[1].reversed().replaceFirstChar { it.uppercase() }.replaceAfter('u', "m") + " "
    val city3 = cityNameArr[2].reversed().replaceFirstChar { it.uppercase() }.replaceAfter('e', "d")
    println(city1 + city2 + city3)
}
private fun String.stringOperationOptimized1(): String {
    return this.split(" ").map { name ->
        name.reversed().lowercase().replaceFirstChar { it.uppercase() }
    }.joinToString(" ")
}
private fun String.stringOperationOptimized2(): String {
    return this.split(" ").joinToString(" ") { name ->
        name.reversed().lowercase().replaceFirstChar { it.uppercase() }
    }
}

private fun arrayOperation() {
    var cityList = mutableListOf("Kolkata", "Mumbai", "Delhi", "Kolkata")
    val kList = cityList.filter { it == "Kolkata" }
    val mList = cityList.filter { it == "Mumbai" }
    val dList = cityList.filter { it == "Delhi" }
    val kolkata = kList.count().toString() + kList.first()
    val mumbai = mList.count().toString() + mList.first()
    val delhi = dList.count().toString() + dList.first()
    cityList = cityList.distinct().toMutableList().also { it ->
        it.removeAt(0)
        it.add(0, kolkata)
        it.removeAt(1)
        it.add(1, mumbai)
        it.removeAt(2)
        it.add(2, delhi)
    }
    println(cityList)
}

private fun arrayOperationOptimized() {
    val cityList = mutableListOf("Kolkata", "Mumbai", "Delhi", "Kolkata")

    // Group by city name and count occurrences
    val counts = cityList.groupingBy { it }.eachCount()

    // Build new list with "count + cityName"
    val result = counts.map { (city, count) -> "$count$city" }

    println(result)
}

/**
 * Write a function to reverse a string without using built‑in functions.
 */
private fun reverseString(value: String) {
    if (value.isEmpty()) {
        println("Empty String")
    } else {
        var reversedStr = ""
        for (i in value.length - 1 downTo 0) {
            reversedStr += value[i]
        }
        println("Reverse String : $reversedStr")
    }
}

/**
 * Write an extension function to check if a string is palindrome.
 * A palindrome is a word, phrase, number, or sequence that reads the same forward and backward.
 * Amit = (amit == tima = false)
 * Level = (level == level = true)
 */
private fun String.isPalindrome(): Boolean {
    val cleanString = this.lowercase().replace("\\s".toRegex(), "")
    return cleanString == cleanString.reversed()
}

/**
 * Demonstrate null safety with ?., ?:, and !!.
 *
 * ## Safe Call Operator (?.)
 * - Executes the call only if the object is not null.
 * - If the object is null, it returns null instead of throwing an exception.
 * - Example:
 *
 * fun safeCallExample() {
 *     val name: String? = null
 *     println(name?.length) // Output: null (no crash)
 * }
 *
 * ## Elvis Operator (?:)
 * - Provides a default value if the expression on the left is null.
 * - Named after Elvis Presley’s hair (?:).
 * - Example:
 *
 * fun elvisExample() {
 *     val name: String? = null
 *     val length = name?.length ?: 0
 *     println(length) // Output: 0 (default value used)
 * }
 *
 * ## Not-Null Assertion Operator (!!)
 * - Converts any value to a non-null type and throws an exception if the value is null.
 * - Dangerous: If the value is null, it throws a NullPointerException.
 * - Example:
 *
 * fun nonNullAssertionExample() {
 *     val name: String? = null
 *     println(name!!.length) // ❌ Throws NullPointerException
 * }
 *
 */
private fun operatorCheck() {
    val name: String? = "Amit"

    // Safe call
    println(name?.uppercase()) // Output: AMIT

    // Elvis operator
    val length = name?.length ?: -1
    println("Length: $length") // Output: Length: 4

    // Non-null assertion
    println(name!!.reversed()) // Output: timA
}

/**
 * Find duplicates in a list and remove them.
 */
private fun <T> removeDuplicateUsingDistinct(list: List<T>): List<T> {
    return list.distinct()
}

private fun <T> removeDuplicateUsingSet(list: List<T>): List<T> {
    return list.toSet().toList()
}

/**
 * Use groupingBy and eachCount to count occurrences of words in a sentence.
 */
private fun groupingByEachCount(sentence: String) {
    val occurance = sentence.replace("\\s".toRegex(), "").lowercase()
        .groupingBy { it }.eachCount().map { (key, value) -> "$key : $value" }
    println(occurance)
}

/**
 * Implement filtering and mapping on a list of objects.
 */
private fun filterAndMap() {
    val users = listOf(
        UserDt(1, "Alice", 25),
        UserDt(2, "Bob", 30),
        UserDt(3, "Charlie", 35),
        UserDt(4, "David", 28),
        UserDt(5, "Eve", 32),
        UserDt(6, "Frank", 29),
        UserDt(7, "Grace", 31),
        UserDt(8, "Hank", 27),
    )

    val filteredUsers = users.filter { it.age >= 30 }
    val names = filteredUsers.map { it.name }
    println(filteredUsers)
    println(names)
}

sealed class Either<out Success, out Failure> {
    data class Success<out Success>(val value: Success) : Either<Success, Nothing>()
    data class Failure<out Failure>(val error: Failure) : Either<Nothing, Failure>()
}

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

private fun printState(state: Result<String>) {
    when (state) {
        is Result.Success -> println("Success : ${state.value}")
        is Result.Error -> println("Error : ${state.message}")
        is Result.Loading -> println("Loading...")
    }
}

private fun printEither(state: Either<String, String>) {
    when (state) {
        is Either.Success -> println("Success : ${state.value}")
        is Either.Failure -> println("Error : ${state.error}")
    }
}

/**
 * Show how coroutines work: launch a coroutine and fetch data asynchronously.
 */
private fun coroutinesAsync() = runBlocking{
    println("Main Starts : ${Thread.currentThread().name}")
    val job = launch {
        delay(1000) // Simulate a long-running task
        println("Fetched data asynchronously on: ${Thread.currentThread().name}")
    }
    job.join()
    println("Main Ends : ${Thread.currentThread().name}")
}

/**
 * Difference between == and === in Kotlin — demonstrate with code.
 */
private fun differenceCheck() {
    val a = String("Amit".toCharArray())
    val b = String("Amit".toCharArray())
    println(a == b) // true → values are equal
    println(a === b) // false → different objects in memory
    val c = a
    println(a === c) // true → same object in memory
}

/**
 * Write a higher‑order function that takes another function as a parameter.
 */
private fun operateNumbers(a: Int, b: Int, operation: (Int, Int) -> Int) : Int{
    return operation(a, b)
}

private fun printNumbersOperation() {
    println(operateNumbers(5, 3) {
        a, b -> a + b
    })
    println(operateNumbers(5, 3) {
            a, b -> a - b
    })
}

data class UserDt(val id: Int, val name: String, val age: Int)




