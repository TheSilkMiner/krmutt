package net.thesilkminer.krmutt.grammar

import net.thesilkminer.krmutt.grammar.collection.MutableNamedObjectCollection
import net.thesilkminer.krmutt.grammar.collection.SelfNamedObjectProvider
import net.thesilkminer.krmutt.grammar.rule.SelfRule
import net.thesilkminer.krmutt.grammar.transform.SelfTransform
import net.thesilkminer.krmutt.kts.grammar.GrammarPack
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.grammar.GrammarTransform
import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection

internal class ScriptPack : GrammarPack {
    override val rules: NamedObjectCollection<GrammarRule> = MutableNamedObjectCollection { c, n -> SelfNamedObjectProvider(c, n) { d -> SelfRule(n, d) } }
    override val transforms: NamedObjectCollection<GrammarTransform> = MutableNamedObjectCollection { c, n -> SelfNamedObjectProvider(c, n) { d -> SelfTransform(n, d) } }
}
