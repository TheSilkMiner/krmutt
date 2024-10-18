package net.thesilkminer.krmutt.grammar.transform

import net.thesilkminer.krmutt.grammar.rule.InvocationContext
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform

internal class SelfTransform(private val name: String, private val delegatingFunction: (GrammarTransform) -> GrammarTransform) : InvokableGrammarTransform {
    private val delegate get() = this.delegatingFunction(this).invokable()

    override fun init() = this.delegate.init()
    override fun finish(): InvokableGrammarTransform = this.delegate.finish() // TODO("Verify")

    override fun StringBuilder.invoke(contextProvider: (StringBuilder) -> InvocationContext) {
        with(this@SelfTransform.delegate) {
            this@invoke.invoke(contextProvider)
        }
    }

    override fun equals(other: Any?): Boolean = this === other || (other is SelfTransform && this.name == other.name)
    override fun hashCode(): Int = this.name.hashCode()
    override fun toString(): String = "Ref[${this.name}]"
}
