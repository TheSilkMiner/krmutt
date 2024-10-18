@file:JvmName("ReplacingTransformer")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.grammar.transform.PatternBasedTransformer
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface PatternTransformer : GrammarTransform

// === REPLACEMENT PROVIDES THROUGH ADDITIONAL CHAINING ===
context(TransformBuildingScope)
infix fun PatternTransformer.and(other: PatternTransformer): PatternTransformer = this.impl().and(other.impl())

// === REPLACEMENT PROVIDES THROUGH REGEX ===
context(TransformBuildingScope)
operator fun Regex.div(other: NamedObjectProvider<GrammarRule>): PatternTransformer = this / other()

context(TransformBuildingScope)
operator fun Regex.div(other: GrammarRule): PatternTransformer {
    val entry = PatternBasedTransformer.Entry(this, other.invokable())
    return PatternBasedTransformer(entry)
}

context(TransformBuildingScope)
operator fun Regex.div(other: RuleVariable): PatternTransformer = this / rule { +other }

context(TransformBuildingScope)
operator fun Regex.div(other: String): PatternTransformer = this / rule { +other }

// === REPLACEMENT PROVIDERS THROUGH STRING ===
context(TransformBuildingScope)
operator fun String.div(other: NamedObjectProvider<GrammarRule>): PatternTransformer = this.regex() / other

context(TransformBuildingScope)
operator fun String.div(other: GrammarRule): PatternTransformer = this.regex() / other

context(TransformBuildingScope)
operator fun String.div(other: RuleVariable): PatternTransformer = this.regex() / other

context(TransformBuildingScope)
operator fun String.div(other: String): PatternTransformer = this.regex() / other

// === HELPERS ===
private fun PatternTransformer.impl(): PatternBasedTransformer = this as? PatternBasedTransformer ?: error("Invalid pattern transformer provided")
private fun String.regex(): Regex = this.toRegex(RegexOption.LITERAL)
