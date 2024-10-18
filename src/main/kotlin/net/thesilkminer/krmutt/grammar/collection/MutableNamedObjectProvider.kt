package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider

internal interface MutableNamedObjectProvider<T : Any> : NamedObjectProvider<T> {
    fun set(value: T)
}
