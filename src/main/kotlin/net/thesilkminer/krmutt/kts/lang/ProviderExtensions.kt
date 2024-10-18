@file:JvmName("ProviderExtensions")

package net.thesilkminer.krmutt.kts.lang

fun <T : Any> NamedObjectProvider<T>.getOrElse(other: T): T = this.getOrElse { other }
fun <T : Any> NamedObjectProvider<T>.getOrElse(otherProvider: () -> T): T = this.getOrNull() ?: otherProvider()

fun <T : Any> NamedObjectProvider<T>.getOrThrow(): T = this.getOrThrow(::NoSuchElementException)
fun <T : Any, E : Exception> NamedObjectProvider<T>.getOrThrow(exceptionCreator: () -> E): T = this.getOrElse { throw exceptionCreator() }
