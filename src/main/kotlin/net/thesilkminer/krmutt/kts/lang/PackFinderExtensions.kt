@file:JvmName("PackFinderExtensions")

package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarPack
import kotlin.properties.PropertyDelegateProvider
import kotlin.reflect.KProperty

private class PackageFinderDelegateProvider(private val finder: PackFinder) : PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarPack>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<GrammarPack> {
        return this.finder.require(property.name)
    }
}

val PackFinder.require: PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarPack>>
    get() = PackageFinderDelegateProvider(this)
