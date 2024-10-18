package net.thesilkminer.krmutt.grammar.transform

import net.thesilkminer.krmutt.grammar.rule.InvocationContext
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform

internal interface InvokableGrammarTransform : GrammarTransform {
    fun init() // TODO("")
    fun finish(): InvokableGrammarTransform

    fun StringBuilder.invoke(contextProvider: (StringBuilder) -> InvocationContext)

    override fun hashCode(): Int
    override fun toString(): String
    override fun equals(other: Any?): Boolean
}
