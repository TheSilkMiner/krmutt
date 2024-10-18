@file:JvmName("StringRuleProviders")

package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.building.unaryPlus
import kotlin.reflect.KProperty

context(GrammarScope)
operator fun String.provideDelegate(thisRef: Any?, property: KProperty<*>): NamedObjectProvider<GrammarRule> {
    return this@GrammarScope.rule(property.name) { +this@provideDelegate }
}
