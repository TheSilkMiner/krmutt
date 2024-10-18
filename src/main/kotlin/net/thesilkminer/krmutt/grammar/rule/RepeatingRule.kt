package net.thesilkminer.krmutt.grammar.rule

import kotlin.random.nextInt

internal class RepeatingRule(private val rule: InvokableGrammarRule, private val repeatRange: IntRange) : InvokableGrammarRule {
    override fun init() = this.rule.init()

    override fun InvocationContext.invoke() {
        val amount = randomly { nextInt(this@RepeatingRule.repeatRange) }
        repeat(amount) {
            append(this@RepeatingRule.rule)
        }
    }

    override fun finish(): InvokableGrammarRule = RepeatingRule(this.rule.finish(), this.repeatRange)

    override fun equals(other: Any?): Boolean = this === other || (other is RepeatingRule && this.rule == other.rule && this.repeatRange == other.repeatRange)
    override fun hashCode(): Int = this.rule.hashCode() * 31 + this.repeatRange.hashCode()
    override fun toString(): String = "${this.rule}[${this.repeatRange}]"
}
