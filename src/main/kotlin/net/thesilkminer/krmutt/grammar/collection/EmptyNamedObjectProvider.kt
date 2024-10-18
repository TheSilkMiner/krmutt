@file:JvmName("EmptyNamedObjectProviderFactory")

package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.getOrThrow

internal interface EmptyNamedObjectProvider<T : Any> : NamedObjectProvider<T> {
    override fun get(): T = this.getOrThrow()
    override fun getOrNull(): T? = null
    override fun configure(configuration: T.() -> Unit): NamedObjectProvider<T> = this
}

private object SharedEmptyNamedObjectProvider : EmptyNamedObjectProvider<Any>

@Suppress("UNCHECKED_CAST")
internal fun <T : Any> EmptyNamedObjectProvider(): EmptyNamedObjectProvider<T> = SharedEmptyNamedObjectProvider as EmptyNamedObjectProvider<T>
