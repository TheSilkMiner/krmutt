package net.thesilkminer.krmutt.grammar.rule

internal class FinalizedRule(private val rule: InvokableGrammarRule): InvokableGrammarRule by rule {
    override fun finish(): InvokableGrammarRule = FinalizedRule(this.rule.finish())

    override fun equals(other: Any?): Boolean = this === other || (other is FinalizedRule && this.rule == other.rule)
    override fun hashCode(): Int = this.rule.hashCode()
    override fun toString(): String = "(${this.rule}).r"
}
