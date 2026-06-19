package com.aroy.interview_prep.Interview_preparation

/**
 * Created by Amit Roy on Date : 27/12/25
 */
fun main() {
    val number = 5
    val output = number.process(
        /**
         * `action` is inlined here and disallowed to non-local return to main that's why
         *  return@process is indicating local return only
         */
        action = { return@process it * 2 },
        reusable = { return@process it + 10 }
    )
    println("Final Result : $output")

    val name = "Amit"
    val output2 = name.process(
        /**
         * `action` is inlined here and disallowed to non-local return to main that's why
         *  return@process is indicating local return only / no issue if we not to mention return@process
         *  without return@process does same
         */
        action = { it.uppercase() },
        reusable = { "$it Roy" }
    )
    println("Final Result : $output2")

    /**
     * Without inline its creating instance which consume memory after byte code generation
     * from tools -> kotlin -> show byte code
     *
     * private static final Unit main$lambda$4() {
     *       System.out.println("This is message");
     *       return Unit.INSTANCE;
     *    }
     */
    message { println("This is message") }

    /**
     * With inline its not creating instance which not consume memory, that is the optimize
     * use of inline to save memory as far as limited mobile storage
     * In main its directly copy and paste the lambda body and print the value
     * without creating instance of lambda object.
     *
     * byte code generation
     * from tools -> kotlin -> show byte code
     *
     * public static final void main() {
     *       int number = 5;
     *       Integer $this$process$iv = number;
     *       Function1 reusable$iv = InlineFunctionExampleKt::main$lambda$1;
     *       int $i$f$process = 0;
     *       int it = ((Number)$this$process$iv).intValue();
     *       int var6 = 0;
     *       it *= 2;
     *       Object storedLambda$iv = reusable$iv.invoke($this$process$iv);
     *       System.out.println("Stored lambda: " + storedLambda$iv);
     *       System.out.println("Final Result : " + it);
     *       String name = "Amit";
     *       Function1 reusable$iv = InlineFunctionExampleKt::main$lambda$3;
     *       int $i$f$process = 0;
     *       int var8 = 0;
     *       String var10000 = name.toUpperCase(Locale.ROOT);
     *       Intrinsics.checkNotNullExpressionValue(var10000, "toUpperCase(...)");
     *       Object result$iv = var10000;
     *       Object storedLambda$iv = reusable$iv.invoke(name);
     *       System.out.println("Stored lambda: " + storedLambda$iv);
     *       System.out.println("Final Result : " + result$iv);
     *       message(InlineFunctionExampleKt::main$lambda$4);
     *       $i$f$process = 0;
     *       int var13 = 0;
     *       System.out.println("This is message2");
     *    }
     */
    message2 { println("This is message2") }

    /**
     * Demonstrates how an inline function allows non-local returns.
     *
     * In this example:
     * ```
     * b {
     *     println("This is message with inline non local return")
     *     return
     * }
     * a()
     * ```
     *
     * - `b` is declared as an **inline function** that takes a lambda.
     * - Because the lambda is inlined, a `return` inside the lambda does not just exit the lambda,
     *   it performs a **non-local return** — meaning it returns from the *caller* of `b` (here, `main`).
     * - As a result, once `return` is executed inside `b`'s lambda, the `main` function terminates
     *   immediately and any subsequent code (like the call to `a()`) is skipped.
     *
     * ### Key points:
     * - **Inline lambdas** allow non-local returns: `return` exits the outer function.
     * - This can be powerful but also dangerous if not intended, because it changes control flow.
     * - To prevent this behavior, you can mark the lambda parameter as `crossinline`, which disallows
     *   non-local returns and forces only local returns (`return@b`).
     *
     * ### Summary:
     * - Here, the `return` inside `b`'s lambda causes `main` to stop execution,
     *   which is why `a()` never runs.
     */
    c {
        println("This is message with inline local return using crossinline")
        return@c
    }
    b {
        println("This is message with inline non local return")
        return
    }
    a()

    /**
     * Output :
     * Stored lambda: 15
     * Final Result : 10
     * Stored lambda: Amit Roy
     * Final Result : AMIT
     * This is message
     * This is message2
     * This is message with inline local return using crossinline
     * This is message with inline non local return
     *
     * a() skipped here because b function lambda returns non-local returns
     * which breaks the next line execution as the lambda object return is nothing but of
     * main() function return which terminate the next line execution
     */
}

/**
 * Extension function that demonstrates the use of `inline`, `crossinline`, and `noinline` lambdas.
 *
 * ### Why inline?
 * - Marking a function as `inline` tells the compiler to copy the function body and inline lambdas
 *   directly at the call site.
 * - This avoids creating lambda objects at runtime and can improve performance.
 *
 * ### Non-local returns
 * - In a normal inline lambda, you can use a `return` statement inside the lambda to exit not only
 *   the lambda but also the outer function. This is called a *non-local return*.
 * - Example:
 *   ```kotlin
 *   inline fun run(block: () -> Unit) {
 *       block() // if block calls return, it exits run()
 *   }
 *   ```
 *
 * ### Why `crossinline`?
 * - Sometimes non-local returns are unsafe (e.g., when the lambda is executed later in another thread
 *   or coroutine). To prevent this, Kotlin provides `crossinline`.
 * - A `crossinline` lambda is still inlined, but the compiler disallows non-local returns.
 * - You can only use `return@label` (local return) inside it.
 *
 * ### Why `noinline`?
 * - In an inline function, all lambdas are normally inlined. But if you need to *store* a lambda in
 *   a variable or *pass it around* to another function, it cannot be inlined.
 * - Marking a parameter as `noinline` keeps it as a real lambda object, allowing reuse.
 *
 * ### Summary
 * - `inline` → performance, allows non-local returns.
 * - `crossinline` → inline but restricts non-local returns for safety.
 * - `noinline` → keeps lambda as object for reuse, cannot be inlined.
 *
 * In this function:
 * - `action` is `crossinline`: inlined for performance, but only local returns (`return@process`) are allowed.
 * - `reusable` is `noinline`: kept as an object so it can be stored or passed, but also only local returns are allowed.
 */
private inline fun <T> T.process(
    crossinline action: (T) -> T,
    noinline reusable: (T) -> T
): T {
    // `action` is inlined directly at call site
    val result = action(this)
    // `reusable` is kept as a lambda object → can be stored/passed
    return reusable(result)
}

private fun message(a: () -> Unit) {
    a.invoke()
}

private inline fun message2(a: () -> Unit) {
    a.invoke()
}

/**
 * Local return
 */
fun a() {
    println("This is message without inline local return")
    return
}

/**
 * Non local return
 */
inline fun b(a: () -> Unit) {
    a.invoke()
}

/**
 * Now inline function make to local returns just need to pass the
 * crossinline as lambda, its basically to prevent the non local returns
 */
inline fun c(crossinline a: () -> Unit) {
    a.invoke()
}