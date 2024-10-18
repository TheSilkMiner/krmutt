@file:JvmName("ScriptPackHolderFactory")

package net.thesilkminer.krmutt.grammar

import net.thesilkminer.krmutt.grammar.collection.KnownEntriesNamedObjectCollection
import net.thesilkminer.krmutt.kts.eval.ScriptFile
import net.thesilkminer.krmutt.kts.grammar.GrammarPack
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.PackFinder

internal class ScriptPackHolder(packs: Map<String, ScriptPack>) : PackFinder {
    private val packs = KnownEntriesNamedObjectCollection(packs)

    override fun require(name: String): NamedObjectProvider<GrammarPack> = this.packs[name]
}

internal fun ScriptPackHolder(scriptFiles: List<ScriptFile>): ScriptPackHolder {
    return ScriptPackHolder(scriptFiles.associate { it.packName to ScriptPack() })
}
