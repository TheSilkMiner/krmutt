@file:JvmName("ExtendedSelectors")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.collection.DirectNamedObjectProvider
import net.thesilkminer.krmutt.grammar.rule.LateSelectionRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

// === EXTENDED SELECTORS ===
context(RuleBuildingScope)
operator fun NamedObjectCollection<GrammarRule>.get(rule: NamedObjectProvider<GrammarRule>): NamedObjectProvider<GrammarRule> = this[rule()]

context(RuleBuildingScope)
operator fun NamedObjectCollection<GrammarRule>.get(rule: GrammarRule): NamedObjectProvider<GrammarRule> = LateSelectionRule(this, rule.invokable()).provider()

context(RuleBuildingScope)
operator fun NamedObjectCollection<GrammarRule>.get(rule: RuleVariable): NamedObjectProvider<GrammarRule> = this[+rule]

// String is already overloaded and resolves directly to a rule, so it is not provided here

// === HELPERS ===
private fun LateSelectionRule.provider(): NamedObjectProvider<GrammarRule> = DirectNamedObjectProvider(this)
