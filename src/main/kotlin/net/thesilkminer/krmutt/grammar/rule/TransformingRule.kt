package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.grammar.transform.InvokableGrammarTransform

internal class TransformingRule(private val rule: InvokableGrammarRule, private val transform: InvokableGrammarTransform) : InvokableGrammarRule {
    override fun init() {
        this.rule.init()
        this.transform.init()
    }

    override fun InvocationContext.invoke() {
        val expandedRule = expandRule(this@TransformingRule.rule)
        append(processTransform(expandedRule, this@TransformingRule.transform))
    }

    override fun finish(): InvokableGrammarRule = TransformingRule(this.rule.finish(), this.transform.finish())

    override fun equals(other: Any?): Boolean = this === other || (other is TransformingRule && this.rule == other.rule && this.transform == other.transform)
    override fun hashCode(): Int = this.rule.hashCode() * 31 + this.transform.hashCode()
    override fun toString(): String = "(${this.rule}).transform { ${this.transform} }"

    private fun InvocationContext.expandRule(rule: InvokableGrammarRule): StringBuilder {
        val builder = StringBuilder()
        val newContext = this.copy(builder = builder)
        newContext.append(rule)
        return builder
    }

    private fun InvocationContext.processTransform(builder: StringBuilder, transform: InvokableGrammarTransform): StringBuilder {
        with(transform) {
            builder.invoke { this@processTransform.copy(builder = it) }
        }
        return builder
    }
}
