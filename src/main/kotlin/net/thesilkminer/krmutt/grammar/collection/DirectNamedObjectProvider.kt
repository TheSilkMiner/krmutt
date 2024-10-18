package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider

internal class DirectNamedObjectProvider<T : Any>(private val `object`: T) : NamedObjectProvider<T> {
    override fun get(): T = this.`object`
    override fun getOrNull(): T = this.get()

    override fun configure(configuration: T.() -> Unit): NamedObjectProvider<T> {
        configuration(this.`object`)
        return this
    }
}