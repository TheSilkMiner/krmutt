@file:JvmName("StringOperations")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.LiteralRule
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

// === LITERAL ===
context(RuleBuildingScope)
operator fun String.unaryPlus(): GrammarRule = LiteralRule(this)

// === ANONYMOUS REDIRECTS ===
context(RuleBuildingScope)
val String.rule: GrammarRule get() = (+this).rule

context(RuleBuildingScope)
val String.r: GrammarRule get() = (+this).r

context(RuleBuildingScope)
val String.anon: GrammarRule get() = (+this).anon

// === ALTERNATIVE BUILDER REDIRECTS ===
context(RuleBuildingScope)
operator fun String.times(weight: Int): AlternativeBuilder = (+this) * weight

context(RuleBuildingScope)
operator fun String.rem(percentage: Double): AlternativeBuilder = (+this) % percentage

// === ALTERNATIVE RULE REDIRECTS ===
context(RuleBuildingScope)
infix fun String.or(other: NamedObjectProvider<GrammarRule>): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun String.or(other: GrammarRule): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun String.or(other: RuleVariable): AlternativeGrammarRule = (+this) or other

context(RuleBuildingScope)
infix fun String.or(other: String): AlternativeGrammarRule = (+this) or other

// === CONCATENATING RULE REDIRECTS WITH .. ===
context(RuleBuildingScope)
operator fun String.rangeTo(other: NamedObjectProvider<GrammarRule>): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun String.rangeTo(other: GrammarRule): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun String.rangeTo(other: RuleVariable): ConcatenatingGrammarRule = (+this)..other

context(RuleBuildingScope)
operator fun String.rangeTo(other: String): ConcatenatingGrammarRule = (+this)..other

// === REPEAT RULE REDIRECTS ===
context(RuleBuildingScope)
operator fun String.get(range: IntRange): GrammarRule = (+this)[range]

context(RuleBuildingScope)
val String.optional: GrammarRule get() = (+this).optional

context(RuleBuildingScope)
val String.opt: GrammarRule get() = (+this).opt

context(RuleBuildingScope)
val String.few: GrammarRule get() = (+this).few

context(RuleBuildingScope)
val String.optionalFew: GrammarRule get() = (+this).optionalFew

context(RuleBuildingScope)
val String.optFew: GrammarRule get() = (+this).optFew
