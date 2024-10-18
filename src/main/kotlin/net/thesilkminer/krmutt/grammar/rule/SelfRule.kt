package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.grammar.GrammarRule

internal class SelfRule(private val name: String, private val delegatingFunction: (GrammarRule) -> GrammarRule) : InvokableGrammarRule {
    private val delegate get() = this.delegatingFunction(this).invokable()

    override fun init() = this.delegate.init()
    override fun finish(): InvokableGrammarRule = this.delegate.finish() // TODO("Verify")

    override fun InvocationContext.invoke() {
        with(this@SelfRule.delegate) {
            this@invoke.invoke()
        }
    }

    override fun equals(other: Any?): Boolean = this === other || (other is SelfRule && this.name == other.name)
    override fun hashCode(): Int = this.name.hashCode()
    override fun toString(): String = "Ref[${this.name}]"
}