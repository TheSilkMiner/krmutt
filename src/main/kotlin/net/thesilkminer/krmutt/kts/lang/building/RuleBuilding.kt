@file:JvmName("RuleBuilding")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.GrammarRuleFactory
import net.thesilkminer.krmutt.kts.lang.GrammarScope
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import kotlin.properties.PropertyDelegateProvider
import kotlin.reflect.KProperty

interface RuleBuildingScope

private class RuleBuildingObjectProvider(
    private val scope: GrammarScope,
    private val factory: GrammarRuleFactory
) : PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarRule>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<GrammarRule> {
        return this.scope.rule(property.name, this.factory)
    }
}

fun GrammarScope.rule(factory: GrammarRuleFactory): PropertyDelegateProvider<Any?, NamedObjectProvider<GrammarRule>> {
    return RuleBuildingObjectProvider(this, factory)
}

