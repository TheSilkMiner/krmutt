package net.thesilkminer.krmutt.grammar.transform

internal class FinalizedTransformer(private val transformer: InvokableGrammarTransform): InvokableGrammarTransform by transformer {
    override fun finish(): InvokableGrammarTransform = FinalizedTransformer(this.transformer.finish())

    override fun equals(other: Any?): Boolean = this === other || (other is FinalizedTransformer && this.transformer == other.transformer)
    override fun hashCode(): Int = this.transformer.hashCode()
    override fun toString(): String = "(${this.transformer})"
}
