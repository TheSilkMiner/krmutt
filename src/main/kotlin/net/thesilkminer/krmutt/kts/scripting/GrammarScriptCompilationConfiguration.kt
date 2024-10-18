package net.thesilkminer.krmutt.kts.scripting

import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.GrammarScope
import net.thesilkminer.krmutt.kts.lang.PackFinder
import net.thesilkminer.krmutt.kts.lang.building.RuleBuildingScope
import kotlin.reflect.typeOf
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.baseClass
import kotlin.script.experimental.api.compilerOptions
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.implicitReceivers

internal class GrammarScriptCompilationConfiguration : ScriptCompilationConfiguration({
    baseClass(GrammarScript::class)
    compilerOptions("-Xlambdas=indy")
    defaultImports(
        "${GrammarScope::class.java.`package`!!.name}.*",
        "${GrammarRule::class.java.`package`!!.name}.*",
        "${RuleBuildingScope::class.java.`package`!!.name}.*",
        PackName::class.qualifiedName!!
    )
    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
    implicitReceivers(typeOf<GrammarScope>(), typeOf<PackFinder>())
})
