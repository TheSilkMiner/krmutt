@file:JvmName("VariableRules")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.BoundVariableRule
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

// === VALUE ===
context(RuleBuildingScope)
operator fun RuleVariable.unaryPlus(): GrammarRule = BoundVariableRule(this)

// === ANONYMOUS REDIRECTS ===
context(RuleBuildingScope)
val RuleVariable.rule: GrammarRule get() = (+this).rule

context(RuleBuildingScope)
val RuleVariable.r: GrammarRule get() = (+this).r

context(RuleBuildingScope)
val RuleVariable.anon: GrammarRule get() = (+this).anon

// === ALTERNATIVE BUILDER REDIRECTS ===
context(RuleBuildingScope)
operator fun RuleVariable.times(weight: Int): AlternativeBuilder = (+this) * weight

context(RuleBuildingScope)
operator fun RuleVariable.rem(percentage: Double): AlternativeBuilder = (+this) % percentage

// === ALTERNATIVE RULE REDIRECTS ===
context(RuleBuildingScope)
infix fun RuleVariable.or(other: NamedObjectProvider<GrammarRule>): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun RuleVariable.or(other: GrammarRule): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun RuleVariable.or(other: RuleVariable): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun RuleVariable.or(other: String): AlternativeGrammarRule = (+this) or other

// === CONCATENATING RULE REDIRECTS WITH .. ===
context(RuleBuildingScope)
operator fun RuleVariable.rangeTo(other: NamedObjectProvider<GrammarRule>): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun RuleVariable.rangeTo(other: GrammarRule): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun RuleVariable.rangeTo(other: RuleVariable): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun RuleVariable.rangeTo(other: String): ConcatenatingGrammarRule = (+this)..other

// === REPEAT RULE REDIRECTS ===
context(RuleBuildingScope)
operator fun RuleVariable.get(range: IntRange): GrammarRule = (+this)[range]

context(RuleBuildingScope)
val RuleVariable.optional: GrammarRule get() = (+this).optional

context(RuleBuildingScope)
val RuleVariable.opt: GrammarRule get() = (+this).opt

context(RuleBuildingScope)
val RuleVariable.few: GrammarRule get() = (+this).few

context(RuleBuildingScope)
val RuleVariable.optionalFew: GrammarRule get() = (+this).optionalFew

context(RuleBuildingScope)
val RuleVariable.optFew: GrammarRule get() = (+this).optFew
