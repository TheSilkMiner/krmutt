@file:JvmName("RuleNamedProviderRedirects")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

// === ANONYMOUS ===
context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.rule get() = this().rule

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.r get() = this().r

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.anon get() = this().anon

// === ALTERNATIVE BUILDERS ===
context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.times(weight: Int): AlternativeBuilder = this() * weight

context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.rem(percentage: Double): AlternativeBuilder = this() % percentage

// === RULE + ALTERNATIVE BUILDER ===
context(RuleBuildingScope)
infix fun NamedObjectProvider<GrammarRule>.or(other: AlternativeBuilder): AlternativeGrammarRule = this() or other

// === ALTERNATIVES ===
context(RuleBuildingScope)
infix fun NamedObjectProvider<GrammarRule>.or(other: NamedObjectProvider<GrammarRule>): AlternativeGrammarRule = this() or other

context(RuleBuildingScope)
infix fun NamedObjectProvider<GrammarRule>.or(other: GrammarRule): AlternativeGrammarRule = this() or other

context(RuleBuildingScope)
infix fun NamedObjectProvider<GrammarRule>.or(other: String): AlternativeGrammarRule = this() or other

// === CONCATENATING RULE REDIRECTS WITH .. ===
context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.rangeTo(other: NamedObjectProvider<GrammarRule>): ConcatenatingGrammarRule = this()..other

context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.rangeTo(other: GrammarRule): ConcatenatingGrammarRule = this()..other

context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.rangeTo(other: RuleVariable): ConcatenatingGrammarRule = this()..other

context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.rangeTo(other: String): ConcatenatingGrammarRule = this()..other

// === REPEAT RULE REDIRECTS ===
context(RuleBuildingScope)
operator fun NamedObjectProvider<GrammarRule>.get(range: IntRange): GrammarRule = this()[range]

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.optional: GrammarRule get() = this().optional

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.opt: GrammarRule get() = this().opt

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.few: GrammarRule get() = this().few

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.optionalFew: GrammarRule get() = this().optionalFew

context(RuleBuildingScope)
val NamedObjectProvider<GrammarRule>.optFew: GrammarRule get() = this().optFew
