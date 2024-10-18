@file:JvmName("Variables")

package net.thesilkminer.krmutt.kts.lang

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private data class NamedRuleVariable(private val scope: GrammarScope, override val name: String) : RuleVariable {
    override fun toString(): String = this.name
}

private class VariableDelegate(private val variable: RuleVariable) : ReadOnlyProperty<Any?, RuleVariable> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): RuleVariable = this.variable
}

private class VariableDelegateProvider(private val scope: GrammarScope) : PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, RuleVariable> = VariableDelegate(NamedRuleVariable(this.scope, property.name))
}

val GrammarScope.variables: PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> get() = VariableDelegateProvider(this)
val GrammarScope.references: PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> get() = this.variables
val GrammarScope.vars: PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> get() = this.variables
val GrammarScope.refs: PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> get() = this.variables
val GrammarScope.ref: PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, RuleVariable>> get() = this.variables
