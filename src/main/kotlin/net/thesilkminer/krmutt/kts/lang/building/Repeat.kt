@file:JvmName("Repeat")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.RepeatingRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule

// === SIMPLE REPEATING RULE ===
context(RuleBuildingScope)
operator fun GrammarRule.get(range: IntRange): GrammarRule {
    check(!range.isEmpty()) { "Range specified for repeat is empty" }
    return RepeatingRule(this.invokable(), range)
}

// === COMMON REPEATING RULES ===
context(RuleBuildingScope)
val GrammarRule.optional: GrammarRule get() = this[0..1]

context(RuleBuildingScope)
val GrammarRule.opt: GrammarRule get() = this.optional

context(RuleBuildingScope)
val GrammarRule.few: GrammarRule get() = this[1..5]

context(RuleBuildingScope)
val GrammarRule.optionalFew: GrammarRule get() = this[0..5]

context(RuleBuildingScope)
val GrammarRule.optFew: GrammarRule get() = this.optionalFew
