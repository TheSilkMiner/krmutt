package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider

internal class KnownEntriesNamedObjectCollection<T : Any>(objectMap: Map<String, T>) : NamedObjectCollection<T> {
    private val map = objectMap.mapValues { DirectNamedObjectProvider(it.value) }

    override val objects: Map<String, NamedObjectProvider<T>> get() = this.map.toMap()

    override fun named(name: String): NamedObjectProvider<T> = this.map[name] ?: EmptyNamedObjectProvider()
}
