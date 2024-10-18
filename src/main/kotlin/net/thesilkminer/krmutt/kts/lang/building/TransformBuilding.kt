@file:JvmName("TransformBuilding")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.kts.grammar.GrammarTransform
import net.thesilkminer.krmutt.kts.lang.GrammarScope
import net.thesilkminer.krmutt.kts.lang.GrammarTransformFactory
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.reflect.KProperty

interface TransformBuildingScope

private class TransformBuildingObjectProvider(
    private val scope: GrammarScope,
    private val factory: GrammarTransformFactory
) : PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarTransform>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<GrammarTransform> {
        return this.scope.transform(property.name, this.factory)
    }
}

fun GrammarScope.transform(factory: GrammarTransformFactory): PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarTransform>> {
    return TransformBuildingObjectProvider(this, factory)
}
