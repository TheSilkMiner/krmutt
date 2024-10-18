package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.grammar.collection.overlay
import net.thesilkminer.krmutt.kts.lang.RuleVariable
import kotlin.random.Random

internal data class InvocationContext(
    private val builder: StringBuilder,
    private val expansionLevel: Long = Long.MAX_VALUE,
    private val random: Random = Random,
    private val context: Map<RuleVariable, InvokableGrammarRule> = emptyMap()
) {
    fun append(other: Any) {
        when (other) {
            is InvokableGrammarRule -> this.append(other)
            is RuleVariable -> this.append(other)
            else -> this.builder.append(other)
        }
    }

    fun append(other: Boolean) {
        this.builder.append(other)
    }

    fun append(other: Byte) {
        this.builder.append(other.toString())
    }

    fun append(other: Char) {
        this.builder.append(other)
    }

    fun append(other: Double) {
        this.builder.append(other)
    }

    fun append(other: Float) {
        this.builder.append(other)
    }

    fun append(other: Int) {
        this.builder.append(other)
    }

    fun append(other: Long) {
        this.builder.append(other)
    }

    fun append(other: Short) {
        this.builder.append(other.toString())
    }

    fun append(other: String) {
        this.builder.append(other)
    }

    fun append(rule: InvokableGrammarRule) {
        val newContext = this.copy(expansionLevel = this.expansionLevel - 1L)
        if (newContext.expansionLevel >= 0L) {
            with(rule) {
                newContext.invoke()
            }
        }
    }

    fun append(variable: RuleVariable) {
        this.append(this.context[variable] ?: error("Variable ${variable.name} is not bound in this context"))
    }

    fun frame(vararg contextPairs: Pair<RuleVariable, InvokableGrammarRule>, newFrame: InvocationContext.() -> Unit) {
        val newContext = this.copy(context = this.context.overlay(contextPairs.toMap()))
        newContext.newFrame()
    }

    fun <R> randomly(provider: Random.() -> R): R {
        return this.random.provider()
    }
}
