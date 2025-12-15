package com.aroy.ebookstore.Interview_preparation

import com.google.gson.Gson

/**
 * Created by Amit Roy on Date : 14/12/25
 */

fun main() {
    printTypeName("Amit")
    printTypeName(3)
    println("Hello".isOfType<String>())
    println(Gson().fromJson<UserData>(json))
}

private val json = "{\"name\": \"Amit\", \"age\": 30}"

private inline fun <reified T> printTypeName(value: T) {
    println("Type name is ${T::class.simpleName}")
}

private inline fun <reified T> Any?.isOfType(): Boolean = this is T

private inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)

data class UserData(val name: String, val age: Int)