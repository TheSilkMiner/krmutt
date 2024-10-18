@file:JvmName("GrammarRuleConnectors")

package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.grammar.GrammarRule

internal fun GrammarRule.invokable(): InvokableGrammarRule = this as? InvokableGrammarRule ?: error("All rules must be invokable")
