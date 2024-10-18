package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.lang.building.ConcatenatingGrammarRule

internal class ConcatenatingRule(private val concatenationList: List<InvokableGrammarRule>) : InvokableGrammarRule, ConcatenatingGrammarRule {
    constructor(first: InvokableGrammarRule) : this(listOf(first))

    override fun init() = this.concatenationList.forEach(InvokableGrammarRule::init)

    override fun InvocationContext.invoke() {
        this@ConcatenatingRule.concatenationList.forEach {
            append(it)
        }
    }

    override fun finish(): InvokableGrammarRule = ConcatenatingRule(this.concatenationList.map(InvokableGrammarRule::finish))

    fun and(other: InvokableGrammarRule): ConcatenatingRule = ConcatenatingRule(this.concatenationList + other)

    override fun equals(other: Any?): Boolean = this === other || (other is ConcatenatingRule && this.concatenationList == other.concatenationList)
    override fun hashCode(): Int = this.concatenationList.hashCode()
    override fun toString(): String = this.concatenationList.joinToString(separator = "..") { "($it)" }
}
