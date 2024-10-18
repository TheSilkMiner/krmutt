@file:JvmName("CollectionExtensions")

package net.thesilkminer.krmutt.kts.lang

import kotlin.properties.PropertyDelegateProvider
import kotlin.reflect.KProperty

private class NamedObjectGettingProvider<T : Any>(
    private val collection: NamedObjectCollection<T>,
    private val configuration: (T.() -> Unit)?
) : PropertyDelegateProvider<Any?, NamedObjectProvider<T>> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<T> {
        val name = property.name
        return this.collection.named(name, this.configuration ?: {})
    }
}

val <T : Any> NamedObjectCollection<T>.naming: PropertyDelegateProvider<Any?, NamedObjectProvider<T>>
    get() = NamedObjectGettingProvider(this, null)

fun <T : Any> NamedObjectCollection<T>.naming(configuration: T.() -> Unit): PropertyDelegateProvider<Any?, NamedObjectProvider<T>> {
    return NamedObjectGettingProvider(this, configuration);
}

fun <T : Any> NamedObjectCollection<T>.named(name: String, configuration: T.() -> Unit): NamedObjectProvider<T> {
    return this.named(name).also { it.configure(configuration) }
}

operator fun <T : Any> NamedObjectCollection<T>.provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<T> {
    return this.naming.provideDelegate(thisRef, property)
}
