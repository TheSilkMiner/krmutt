@file:JvmName("Alternatives")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.AlternativePercentageSpec
import net.thesilkminer.krmutt.grammar.rule.AlternativeRule
import net.thesilkminer.krmutt.grammar.rule.AlternativeSpec
import net.thesilkminer.krmutt.grammar.rule.AlternativeWeightSpec
import net.thesilkminer.krmutt.grammar.rule.RandomAlternativeRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface AlternativeGrammarRule : GrammarRule

interface AlternativeBuilder

private data class AlternativeData(val rule: GrammarRule, val weight: Int? = null, val percentage: Double? = null) : AlternativeBuilder {
    private companion object {
        private val PERCENTAGE_VALIDITY_RANGE = 0.0..1.0
    }

    init {
        if (this.percentage != null) {
            require(this.percentage in PERCENTAGE_VALIDITY_RANGE) { "Percentage ${this.percentage} is not in the valid range $PERCENTAGE_VALIDITY_RANGE" }
        }
    }
}

// === ALTERNATIVE BUILDER CREATORS ===
context(RuleBuildingScope)
operator fun GrammarRule.times(weight: Int): AlternativeBuilder = AlternativeData(this, weight = weight)

context(RuleBuildingScope)
operator fun GrammarRule.rem(percentage: Double): AlternativeBuilder = AlternativeData(this, percentage = percentage)

// === ALTERNATIVE BUILDER ANONYMOUS RULE ===
context(RuleBuildingScope)
val AlternativeBuilder.rule: AlternativeGrammarRule get() = this.data.toAlternativeRule()

context(RuleBuildingScope)
val AlternativeBuilder.r: AlternativeGrammarRule get() = this.rule

context(RuleBuildingScope)
val AlternativeBuilder.anon: AlternativeGrammarRule get() = this.rule

// === ALTERNATIVE BUILDER ALTERNATIVE RULE ===
context(RuleBuildingScope)
infix fun AlternativeBuilder.or(other: AlternativeBuilder): AlternativeGrammarRule = this.rule or other

// === REGULAR RULE + ALTERNATIVE BUILDER ===
context(RuleBuildingScope)
infix fun GrammarRule.or(other: AlternativeBuilder): AlternativeGrammarRule = this.alternative().or(other.data.asRuleAlternative())

// === REGULAR RULE ALTERNATIVE ===
context(RuleBuildingScope)
infix fun GrammarRule.or(other: NamedObjectProvider<GrammarRule>): AlternativeGrammarRule = this or other()

context(RuleBuildingScope)
infix fun GrammarRule.or(other: GrammarRule): AlternativeGrammarRule = this or other * 1

context(RuleBuildingScope)
infix fun GrammarRule.or(other: RuleVariable): AlternativeGrammarRule = this or +other

context(RuleBuildingScope)
infix fun GrammarRule.or(other: String): AlternativeGrammarRule = this or +other

// === HELPERS ===
context(RuleBuildingScope)
private fun GrammarRule.alternative(): AlternativeRule = if (this is AlternativeRule) this else (this * 1).data.toAlternativeRule()

private val AlternativeBuilder.data: AlternativeData get() = this as? AlternativeData ?: error("All alternatives must be data")
private fun AlternativeData.toAlternativeRule(): AlternativeRule = RandomAlternativeRule(this.asRuleAlternative())

private fun AlternativeData.asRuleAlternative(): AlternativeSpec {
    check(this.weight != null || this.percentage != null)
    check(this.weight == null || this.percentage == null)
    val spec = if (this.weight != null) AlternativeWeightSpec(this.weight) else AlternativePercentageSpec(this.percentage!!)
    return AlternativeSpec(this.rule.invokable(), spec)
}
