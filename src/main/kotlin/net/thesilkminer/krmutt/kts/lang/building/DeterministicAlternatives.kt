@file:JvmName("DeterministicAlternatives")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.AlternativeRule
import net.thesilkminer.krmutt.grammar.rule.DeterministicAlternativeRule
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface DeterministicAlternativeGrammarRule : AlternativeGrammarRule

// === DETERMINISTIC CREATORS ===
context(RuleBuildingScope)
val AlternativeGrammarRule.det: DeterministicAlternativeGrammarRule get() = if (this is DeterministicAlternativeRule) this else DeterministicAlternativeRule(this.alternative())

context(RuleBuildingScope)
val AlternativeGrammarRule.deterministic: DeterministicAlternativeGrammarRule get() = this.det

// === SHUFFLERS ===
context(RuleBuildingScope)
fun DeterministicAlternativeGrammarRule.shuffle(): DeterministicAlternativeGrammarRule = this.alternative().applyShuffle(false)

context(RuleBuildingScope)
val DeterministicAlternativeGrammarRule.shuffled: DeterministicAlternativeGrammarRule get() = this.shuffle()

context(RuleBuildingScope)
fun DeterministicAlternativeGrammarRule.shuffling(): DeterministicAlternativeGrammarRule = this.alternative().applyShuffle(true)

// === SHUFFLER NON-DETERMINISTIC REDIRECTS ===
context(RuleBuildingScope)
fun AlternativeGrammarRule.shuffle(): DeterministicAlternativeGrammarRule = this.det.shuffle()

context(RuleBuildingScope)
val AlternativeGrammarRule.shuffled: DeterministicAlternativeGrammarRule get() = this.det.shuffled

context(RuleBuildingScope)
fun AlternativeGrammarRule.shuffling(): DeterministicAlternativeGrammarRule = this.det.shuffling()

// === ALTERNATIVE CLONES TO MAINTAIN TYPING ===
context(RuleBuildingScope)
infix fun DeterministicAlternativeGrammarRule.or(other: AlternativeBuilder): DeterministicAlternativeGrammarRule = (this as AlternativeGrammarRule or other).det

context(RuleBuildingScope)
infix fun DeterministicAlternativeGrammarRule.or(other: NamedObjectProvider<GrammarRule>): DeterministicAlternativeGrammarRule = this or other()

context(RuleBuildingScope)
infix fun DeterministicAlternativeGrammarRule.or(other: GrammarRule): DeterministicAlternativeGrammarRule = this or other * 1

context(RuleBuildingScope)
infix fun DeterministicAlternativeGrammarRule.or(other: RuleVariable): DeterministicAlternativeGrammarRule = this or +other

context(RuleBuildingScope)
infix fun DeterministicAlternativeGrammarRule.or(other: String): DeterministicAlternativeGrammarRule = this or +other

// === HELPERS ===
private fun AlternativeGrammarRule.alternative(): AlternativeRule = this as? AlternativeRule ?: error("Invalid alternative rule in use")
private fun DeterministicAlternativeGrammarRule.alternative(): DeterministicAlternativeRule = this as? DeterministicAlternativeRule ?: error("Invalid alternative rule in use")

private fun DeterministicAlternativeRule.applyShuffle(always: Boolean): DeterministicAlternativeRule {
    val mode = if (always) DeterministicAlternativeRule.ShuffleMode.ALWAYS else DeterministicAlternativeRule.ShuffleMode.ONCE
    return DeterministicAlternativeRule(this, mode)
}
