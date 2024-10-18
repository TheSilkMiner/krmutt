package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.lang.RuleVariable

internal class BoundVariableRule(private val variable: RuleVariable) : InvokableGrammarRule {
    override fun init() = Unit
    override fun finish(): InvokableGrammarRule = this

    override fun InvocationContext.invoke() {
        append(this@BoundVariableRule.variable)
    }

    override fun equals(other: Any?): Boolean = this === other || (other is BoundVariableRule && this.variable == other.variable)
    override fun hashCode(): Int = this.variable.hashCode()
    override fun toString(): String = "+${this.variable.name}"
}