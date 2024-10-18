package net.thesilkminer.krmutt.grammar.transform

import net.thesilkminer.krmutt.grammar.rule.InvocationContext
import net.thesilkminer.krmutt.grammar.rule.InvokableGrammarRule
import net.thesilkminer.krmutt.kts.lang.building.PatternTransformer

internal class PatternBasedTransformer(private val entries: List<Entry>) : InvokableGrammarTransform, PatternTransformer {
    internal data class Entry(val pattern: Regex, val rule: InvokableGrammarRule) {
        override fun toString(): String = "${this.pattern} / rule { ${this.rule} }"
    }

    constructor(entry: Entry) : this(listOf(entry))

    override fun init() = this.entries.forEach { it.rule.init() }

    override fun StringBuilder.invoke(contextProvider: (StringBuilder) -> InvocationContext) {
        this@PatternBasedTransformer.entries.forEach {
            replace(it, contextProvider)
        }
    }

    fun and(entry: PatternBasedTransformer): PatternBasedTransformer = PatternBasedTransformer(this.entries + entry.entries)

    override fun finish(): InvokableGrammarTransform = PatternBasedTransformer(this.entries.map { it.copy(rule = it.rule.finish()) })

    override fun equals(other: Any?): Boolean = this === other || (other is PatternBasedTransformer && this.entries == other.entries)
    override fun hashCode(): Int = this.entries.hashCode()
    override fun toString(): String = this.entries.joinToString(separator = " and ")

    private fun StringBuilder.replace(entry: Entry, contextProvider: (StringBuilder) -> InvocationContext) {
        val computedReplacement by lazy { replacement(entry.rule, contextProvider) }
        replace(entry.pattern) { computedReplacement }
    }

    private fun replacement(rule: InvokableGrammarRule, contextProvider: (StringBuilder) -> InvocationContext): String {
        val builder = StringBuilder()
        val context = contextProvider(builder)

        with(rule) {
            context.invoke()
        }

        return builder.toString()
    }
}
