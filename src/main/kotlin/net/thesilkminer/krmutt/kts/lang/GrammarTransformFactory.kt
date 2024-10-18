@file:JvmName("GrammarTransformFactory")

package net.thesilkminer.krmutt.kts.lang

import net.thesilkminer.krmutt.kts.grammar.GrammarTransform
import net.thesilkminer.krmutt.kts.lang.building.TransformBuildingScope

typealias GrammarTransformFactory = TransformBuildingScope.() -> GrammarTransform
