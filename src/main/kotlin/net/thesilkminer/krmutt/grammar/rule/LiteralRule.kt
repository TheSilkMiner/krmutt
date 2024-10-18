package net.thesilkminer.krmutt.grammar.rule

internal class LiteralRule(private val literal: String): InvokableGrammarRule {
    override fun init() = Unit
    override fun finish(): InvokableGrammarRule = this

    override fun InvocationContext.invoke() {
        append(this@LiteralRule.literal)
    }

    override fun equals(other: Any?): Boolean = this === other || (other is LiteralRule && this.literal == other.literal)
    override fun hashCode(): Int = this.literal.hashCode()
    override fun toString(): String = "+${this.literal}"
}
