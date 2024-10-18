package net.thesilkminer.krmutt.kts.lang

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface NamedObjectProvider<out T : Any> : () -> T, ReadOnlyProperty<Any?, T> {
    fun get(): T
    fun getOrNull(): T?
    fun configure(configuration: T.() -> Unit): NamedObjectProvider<T>

    override fun invoke(): T = this.get()
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = this()
}
