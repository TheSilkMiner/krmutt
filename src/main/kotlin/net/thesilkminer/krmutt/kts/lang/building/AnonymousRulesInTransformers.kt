@file:JvmName("AnonymousRulesInTransformers")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.FinalizedRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.GrammarRuleFactory

private object OnTheFlyRuleBuildingScope : RuleBuildingScope

fun TransformBuildingScope.rule(factory: GrammarRuleFactory): GrammarRule {
    val rule = OnTheFlyRuleBuildingScope.factory()
    return FinalizedRule(rule.invokable()).finish()
}
