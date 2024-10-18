package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform

interface GrammarScope {
    fun rule(name: String, factory: GrammarRuleFactory): NamedObjectProvider<GrammarRule>
    fun transform(name: String, factory: GrammarTransformFactory): NamedObjectProvider<GrammarTransform>

    fun out(ruleName: String)
    fun out(rule: () -> GrammarRule)
}
