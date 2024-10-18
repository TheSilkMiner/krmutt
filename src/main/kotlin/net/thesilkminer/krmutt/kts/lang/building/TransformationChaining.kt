@file:JvmName("TransformationChaining")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.transform.TransformChain
import net.thesilkminer.krmutt.grammar.transform.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform

// === TRANSFORMATION CHAINING ===
context(TransformBuildingScope)
infix fun GrammarTransform.then(other: GrammarTransform): GrammarTransform = this.asChain().add(other.invokable())

// === PRIVATE HELPERS
private fun GrammarTransform.asChain(): TransformChain = if (this is TransformChain) this else TransformChain(this.invokable())
