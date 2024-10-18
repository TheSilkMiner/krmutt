package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection
import net.thesilkminer.krmutt.kts.lang.getOrThrow

internal class LateSelectionRule(
    private val collection: NamedObjectCollection<GrammarRule>,
    private val ruleName: InvokableGrammarRule
) : InvokableGrammarRule {
    override fun init() = this.ruleName.init()

    override fun InvocationContext.invoke() {
        val name = this.resolveName()
        val target = this@LateSelectionRule.collection[name].getOrThrow { NoSuchElementException("Rule obtained by selector $name does not exist") }.invokable()
        append(target)
    }

    override fun finish(): InvokableGrammarRule = LateSelectionRule(this.collection, this.ruleName.finish())

    override fun equals(other: Any?): Boolean = this === other || (other is LateSelectionRule && this.collection == other.collection && this.ruleName == other.ruleName)
    override fun hashCode(): Int = this.ruleName.hashCode() * 31 + this.collection.hashCode()
    override fun toString(): String = "rules[${this.ruleName}]"

    private fun InvocationContext.resolveName(): String {
        val builder = StringBuilder()
        val context = this.copy(builder = builder)
        context.append(this@LateSelectionRule.ruleName)
        return builder.toString()
    }
}
