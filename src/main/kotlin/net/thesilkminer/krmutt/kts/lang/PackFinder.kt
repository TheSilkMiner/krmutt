package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarPack

interface PackFinder {
    fun require(name: String): NamedObjectProvider<GrammarPack>
}
