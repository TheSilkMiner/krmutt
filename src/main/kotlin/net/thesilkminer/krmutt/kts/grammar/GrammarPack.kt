package net.thesilkminer.krmutt.kts.grammar

import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection

interface GrammarPack {
    val rules: NamedObjectCollection<GrammarRule>
    val transforms: NamedObjectCollection<GrammarTransform>
}
