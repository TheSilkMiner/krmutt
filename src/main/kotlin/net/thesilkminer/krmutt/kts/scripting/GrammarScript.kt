package net.thesilkminer.krmutt.kts.scripting

import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    fileExtension = "krmutt.kts",
    compilationConfiguration = GrammarScriptCompilationConfiguration::class
)
abstract class GrammarScript(val packName: String)
