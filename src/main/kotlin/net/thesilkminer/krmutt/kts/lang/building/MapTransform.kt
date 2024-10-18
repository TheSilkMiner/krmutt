@file:JvmName("MapTransform")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.TransformingRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.grammar.transform.FinalizedTransformer
import net.thesilkminer.krmutt.grammar.transform.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.GrammarTransformFactory
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface MapEntry

private data class SimpleMapping(val match: String, val replace: GrammarRule) : MapEntry

private object OnTheFlyTransformBuildingScope : TransformBuildingScope

// === GENERIC TRANSFORMATION CONTEXT ===
context(RuleBuildingScope)
fun GrammarRule.transform(transform: GrammarTransformFactory): GrammarRule {
    val transformer = OnTheFlyTransformBuildingScope.transform()
    val finalizedTransformer = FinalizedTransformer(transformer.invokable()).finish()
    return TransformingRule(this.invokable(), finalizedTransformer)
}

// === SPECIALIZATION FOR MAPPING
context(RuleBuildingScope)
fun GrammarRule.map(vararg entries: MapEntry): GrammarRule {
    TODO()
}

// === STRING DIVISION SUPPORT FOR MAPPING
context(RuleBuildingScope)
operator fun String.div(ruleProvider: NamedObjectProvider<GrammarRule>): MapEntry = this / ruleProvider()

context(RuleBuildingScope)
operator fun String.div(rule: GrammarRule): MapEntry = SimpleMapping(this, rule)

context(RuleBuildingScope)
operator fun String.div(variable: RuleVariable): MapEntry = this / +variable

context(RuleBuildingScope)
operator fun String.div(literal: String): MapEntry = this / +literal
