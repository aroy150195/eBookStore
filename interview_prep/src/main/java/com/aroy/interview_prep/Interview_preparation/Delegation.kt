package com.aroy.interview_prep.Interview_preparation

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

/**
 * Created by Amit Roy on Date : 19/02/26
 */

/**
 * Contract for any Chef who can prepare biryani.
 */
interface Chef {

    /**
     * Prepares the given biryani dish.
     *
     * @param biryaniName Name of the biryani to prepare
     */
    fun prepare(biryaniName: String)
}

/**
 * Concrete implementation of Chef.
 * This class contains the actual business logic for preparing biryani.
 */
class ChefBiryani : Chef {

    /**
     * Prints preparation message for the given biryani.
     */
    override fun prepare(biryaniName: String) {
        println("$biryaniName is ready for serve")
    }
}

/**
 * Traditional approach without delegation.
 *
 * Server directly creates and owns the Chef implementation.
 * Tight coupling: Server is dependent on a concrete Chef class.
 */
class ServerTraditionalApproach {

    private val chef: Chef = ChefBiryani()

    /**
     * Takes order and forwards it to Chef manually.
     *
     * @param biryaniName Name of the biryani ordered
     */
    fun takeOrder(biryaniName: String) {
        chef.prepare(biryaniName)
    }
}

/**
 * Delegation approach using Kotlin built-in delegation.
 *
 * Server delegates all Chef responsibilities to the provided Chef object.
 * This removes boilerplate forwarding code and promotes loose coupling.
 *
 * `: Chef by chef` means:
 * Compiler automatically generates implementation of all Chef methods
 * and forwards calls to the provided `chef` instance.
 *
 * @property chef The Chef implementation to whom work will be delegated
 */
class ServerInBuildDelegationApproach(
    private val chef: Chef = ChefBiryani()
) : Chef by chef

class DelegationExample {
    val l: String by lazy {
        println("Initializing name")
        "Amit"
    }
    var n: String by Delegates.observable("AmitInitial") { prop, old, new ->
        println("$old -> $new")
    }
    var m: String by Delegates.vetoable("AmitInitial") { prop, old, new ->
        println("$old -> $new")
        new != old
    }
}

fun main() = runBlocking{
    val serverTraditionalApproach = ServerTraditionalApproach()
    serverTraditionalApproach.takeOrder("Hyderabadi Biryani")
    val serverInBuildDelegationApproach = ServerInBuildDelegationApproach(ChefBiryani())
    serverInBuildDelegationApproach.prepare("Handi Biryani")
    val delegationExample = DelegationExample()
    delegationExample.l
    delegationExample.n = "AmitInitial2"
    delegationExample.m = "AmitInitial"
    delay(3000)
    delegationExample.m = "AmitInitial3"
    delay(3000)
    delegationExample.m = "AmitInitial3"
}