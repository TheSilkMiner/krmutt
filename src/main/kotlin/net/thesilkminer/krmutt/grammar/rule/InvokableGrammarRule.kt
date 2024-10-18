package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.grammar.GrammarRule

internal interface InvokableGrammarRule : GrammarRule {
    fun init() // TODO("")
    fun finish(): InvokableGrammarRule

    fun InvocationContext.invoke()

    override fun hashCode(): Int
    override fun toString(): String
    override fun equals(other: Any?): Boolean
}
