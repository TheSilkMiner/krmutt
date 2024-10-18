package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider

internal class SelfNamedObjectProvider<T : Any>(
    collection: NamedObjectCollection<T>,
    name: String,
    defaultValueCreator: ((T) -> T) -> T
) : MutableNamedObjectProvider<T> {
    private class SelfReferentialException(name: String) : Exception("Self-reference to $name")

    private val configurations = mutableListOf<T.() -> Unit>()

    private var defaultObject = defaultValueCreator {
        val result = collection[name].get()
        if (result == it) {
            throw SelfReferentialException(name)
        }
        result
    } as T?

    private var currentObject = this.defaultObject!!

    override fun get(): T = this.currentObject

    override fun getOrNull(): T = this.currentObject

    override fun set(value: T) {
        check(!this.hasValue()) { "Object already set" }
        this.currentObject = value
        this.defaultObject = null
        this.configurations.forEach { value.it() }
        this.configurations.clear()
    }

    override fun configure(configuration: T.() -> Unit): NamedObjectProvider<T> {
        if (this.hasValue()) {
            this.currentObject.configuration()
        } else {
            this.configurations += configuration
        }
        return this
    }

    private fun hasValue() = this.defaultObject != null
}