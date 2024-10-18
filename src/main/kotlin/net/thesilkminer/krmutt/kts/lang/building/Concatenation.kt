@file:JvmName("Concatenation")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.ConcatenatingRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface ConcatenatingGrammarRule : GrammarRule

// === REGULAR RULE CONCATENATION WITH .. ===
context(RuleBuildingScope)
operator fun GrammarRule.rangeTo(other: NamedObjectProvider<GrammarRule>): ConcatenatingGrammarRule = this..other()

context(RuleBuildingScope)
operator fun GrammarRule.rangeTo(other: GrammarRule): ConcatenatingGrammarRule = this.concatenation().and(other)

context(RuleBuildingScope)
operator fun GrammarRule.rangeTo(other: RuleVariable): ConcatenatingGrammarRule = this..+other

context(RuleBuildingScope)
operator fun GrammarRule.rangeTo(other: String): ConcatenatingGrammarRule = this..+other

// === HELPERS ===
private fun GrammarRule.concatenation(): ConcatenatingRule = if (this is ConcatenatingRule) this else this.toConcatenatingRule()
private fun GrammarRule.toConcatenatingRule(): ConcatenatingRule = ConcatenatingRule(this.invokable())
private fun ConcatenatingRule.and(other: GrammarRule): ConcatenatingRule = this.and(other.invokable())
