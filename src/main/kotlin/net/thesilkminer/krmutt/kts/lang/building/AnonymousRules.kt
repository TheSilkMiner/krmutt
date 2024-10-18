@file:JvmName("AnonymousRules")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.FinalizedRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule

context(RuleBuildingScope)
fun GrammarRule.finalize(): GrammarRule = if (this is FinalizedRule) this else FinalizedRule(this.invokable())

context(RuleBuildingScope)
val GrammarRule.rule: GrammarRule get() = this.finalize()

context(RuleBuildingScope)
val GrammarRule.r: GrammarRule get() = this.rule

context(RuleBuildingScope)
val GrammarRule.anon: GrammarRule get() = this.rule
