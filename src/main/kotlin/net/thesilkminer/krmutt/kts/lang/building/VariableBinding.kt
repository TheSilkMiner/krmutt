@file:JvmName("VariableBinding")

package net.thesilkminer.krmutt.kts.lang.building

import net.thesilkminer.krmutt.grammar.rule.BindingVariableRule
import net.thesilkminer.krmutt.grammar.rule.invokable
import net.thesilkminer.krmutt.kts.grammar.GrammarRule
import net.thesilkminer.krmutt.kts.lang.GrammarRuleFactory
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.RuleVariable

interface BoundVariable

private data class BoundVariableSpec(val variable: RuleVariable, val binding: GrammarRule, val fixed: Boolean) : BoundVariable

// === VARIABLE BINDING CREATION ===
context(RuleBuildingScope)
infix fun RuleVariable.with(rule: NamedObjectProvider<GrammarRule>): BoundVariable = this with rule()

context(RuleBuildingScope)
infix fun RuleVariable.with(rule: GrammarRule): BoundVariable = BoundVariableSpec(this, rule, false)

context(RuleBuildingScope)
infix fun RuleVariable.with(rule: RuleVariable): BoundVariable = this with +rule

context(RuleBuildingScope)
infix fun RuleVariable.with(rule: String): BoundVariable = this with +rule

// === FIXED BINDING CREATION ===
context(RuleBuildingScope)
infix fun RuleVariable.fixing(rule: NamedObjectProvider<GrammarRule>): BoundVariable = this fixing rule()

context(RuleBuildingScope)
infix fun RuleVariable.fixing(rule: GrammarRule): BoundVariable = BoundVariableSpec(this, rule, true)

context(RuleBuildingScope)
infix fun RuleVariable.fixing(rule: RuleVariable): BoundVariable = this fixing +rule

context(RuleBuildingScope)
infix fun RuleVariable.fixing(rule: String): BoundVariable = this fixing +rule

// === BINDING VARIABLES ===
fun RuleBuildingScope.bind(vararg binds: BoundVariable, bindScope: GrammarRuleFactory): GrammarRule {
    checkBinds(binds)
    val nested = this.bindScope().invokable().finish()
    return BindingVariableRule(binds.map { it.spec.asRuleBind() }, nested)
}

// === HELPERS ===
private fun checkBinds(binds: Array<out BoundVariable>) {
    val variables = mutableSetOf<RuleVariable>()
    binds.forEach {
        val variable = it.spec.variable
        check(!variables.add(variable)) { "Variable $variable has already been bound in the same binding" }
    }
}

private val BoundVariable.spec: BoundVariableSpec get() = this as? BoundVariableSpec ?: error("Bound variables must be specs")
private fun BoundVariableSpec.asRuleBind(): BindingVariableRule.Bind = BindingVariableRule.Bind(this.variable, this.binding.invokable(), this.fixed)
