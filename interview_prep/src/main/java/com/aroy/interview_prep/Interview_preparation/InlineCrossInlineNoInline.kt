package com.aroy.interview_prep.Interview_preparation

/**
 * Created by Amit Roy on Date : 27/12/25
 */
fun main() {
    val videos = listOf(
        Video.Programming("Kotlin Basics", "10:30"),
        Video.Cooking("Cooking with Kotlin", "15:45"),
        Video.Programming("Kotlin for Beginners", "20:12"),
        Video.Travel("Travel vlog : Kotlin Edition", "30:05")
    )
    val filterVideos = filter<Video.Programming>(videos)
    val filterVideos2 = filter2<Video>(videos)
    println(filterVideos.toString())
    println(filterVideos2.toString())
    applyTransformation(
        videos = videos,
        transformation = { video ->
            when(video) {
                is Video.Programming -> {
                    video.copy(title = video.title + " transformed")
                }
                is Video.Cooking -> {
                    video.copy(title = video.title + " transformed")
                }
                is Video.Travel -> {
                    video.copy(title = video.title + " transformed")
                }
            }
        },
        onComplete = { video ->
            println("Transformation completed")
            println(video.toString())
        }
    )

    val videoPlayable = VideoPlayable<Video>()
    videoPlayable.playVideo(videos)
}

sealed class Video() {
    data class Programming(val title: String, val duration: String) : Video()
    data class Cooking(val title: String, val duration: String) : Video()
    data class Travel(val title: String, val duration: String) : Video()
}

/**
 * Filters a list of elements, returning only those that are instances of the generic type [T].
 *
 * ### Why reified?
 * - Normally, generic type parameters like [T] are erased at compile time (type erasure).
 * - This means the JVM does not know the actual type of [T] at runtime, so you cannot
 *   directly check `is T` or call functions like `filterIsInstance<T>()` without extra help.
 * - By marking the function as `inline` and the type parameter as `reified`, the compiler
 *   embeds the actual type information into the bytecode, making it available at runtime.
 *
 * ### How it works:
 * - `inline` → copies the function body at the call site, enabling reification.
 * - `reified` → preserves the type information of [T] at runtime.
 * - `filterIsInstance<T>()` → uses the reified type to filter only elements of type [T].
 *
 * ### Example:
 * ```kotlin
 * val items: List<Any> = listOf("Amit", 42, "Kumar")
 * val strings: List<String> = filter<String>(items)
 * println(strings) // Output: [Amit, Kumar]
 * ```
 * Note : reified only work with inline function
 * @param videos The input list containing elements of potentially mixed types.
 * @return A list containing only elements that are instances of type [T].
 */
inline fun <reified T> filter(videos: List<Video>) : List<T> {
    return videos.filterIsInstance<T>()
}

inline fun <reified T : Video> filter2(videos: List<Video>) : List<T> {
    return videos.filterIsInstance<T>()
}

inline fun applyTransformation(
    videos: List<Video>,
    noinline transformation: (Video) -> Video,
    crossinline onComplete: (List<Video>) -> Unit
) {
    val transformVideo = videos.map { transformation(it) }
    onComplete.invoke(transformVideo)
}

/**
 * A generic class that can play different types of [Video].
 *
 * ### About the `where` keyword:
 * - In Kotlin, generic type parameters can be constrained to specific types or interfaces.
 * - The `where` clause is used when you want to apply constraints on a generic type [T].
 * - In this class, `where T : Video` means that the type parameter [T] must be a subtype of [Video].
 * - This ensures that only classes inheriting from [Video] can be used with [VideoPlayable].
 *
 * ### Why use `where`?
 * - It provides **type safety**: you can only pass lists of objects that are guaranteed to be `Video` or its subclasses.
 * - It allows you to use members of `Video` (like `title`) directly on [T] without casting.
 * - You can also apply **multiple constraints** with `where`, for example:
 *   ```kotlin
 *   class Example<T> where T : Video, T : Comparable<T>
 *   ```
 *   Here [T] must be both a `Video` and implement `Comparable`.
 *
 * ### Example usage:
 * ```kotlin
 * val player = VideoPlayable<Video.Programming>()
 * player.playVideo(listOf(Video.Programming("Kotlin Basics")))
 * ```
 *
 * @param T The type of video that this player can handle. Must be a subtype of [Video].
 */
class VideoPlayable<T> where T: Video {
    fun playVideo(list: List<T>) {
        list.forEach {
            when(it) {
                is Video.Programming -> {
                    println("Playing ${it.title}")
                }
                is Video.Cooking -> {
                    println("Cooking ${it.title}")
                }
                is Video.Travel -> {
                    println("Travel ${it.title}")
                }
            }
        }
    }
}