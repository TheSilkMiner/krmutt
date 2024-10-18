@file:JvmName("GrammarTransformsConnectors")

package net.thesilkminer.krmutt.grammar.transform

import net.thesilkminer.krmutt.kts.grammar.GrammarTransform

internal fun GrammarTransform.invokable(): InvokableGrammarTransform = this as? InvokableGrammarTransform ?: error("All transforms must be invokable")
