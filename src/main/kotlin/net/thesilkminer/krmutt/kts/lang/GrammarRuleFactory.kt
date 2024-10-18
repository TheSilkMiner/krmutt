@file:JvmName("GrammarRuleFactory")

package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.building.RuleBuildingScope

typealias GrammarRuleFactory = RuleBuildingScope.() -> GrammarRule
