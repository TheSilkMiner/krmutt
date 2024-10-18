package net.thesilkminer.krmutt.grammar.transform

import net.thesilkminer.krmutt.grammar.rule.InvocationContext

internal class TransformChain(private val transforms: List<InvokableGrammarTransform>) : InvokableGrammarTransform {
    constructor(transform: InvokableGrammarTransform) : this(listOf(transform))

    override fun init() = this.transforms.forEach(InvokableGrammarTransform::init)

    override fun finish(): InvokableGrammarTransform = TransformChain(this.transforms.map(InvokableGrammarTransform::finish))

    override fun StringBuilder.invoke(contextProvider: (StringBuilder) -> InvocationContext) {
        this@TransformChain.transforms.forEach {
            with(it) {
                this@invoke.invoke(contextProvider)
            }
        }
    }

    fun add(transform: InvokableGrammarTransform): TransformChain = TransformChain(this.transforms + transform)

    override fun equals(other: Any?): Boolean = this === other || (other is TransformChain && this.transforms == other.transforms)
    override fun hashCode(): Int = this.transforms.hashCode()
    override fun toString(): String = this.transforms.joinToString(separator = " then ", prefix = "(", postfix = ")")
}
