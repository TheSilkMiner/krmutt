package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.lang.RuleVariable

internal class BindingVariableRule(
    private val fixedBinds: Collection<Bind>,
    private val variableBinds: Collection<Bind>,
    private val nestedRule: InvokableGrammarRule
) : InvokableGrammarRule {
    constructor(binds: Collection<Bind>, nestedRule: InvokableGrammarRule) : this(binds.filter(Bind::fixed), binds.filterNot(Bind::fixed), nestedRule)

    internal data class Bind(val variable: RuleVariable, val rule: InvokableGrammarRule, val fixed: Boolean) {
        override fun toString(): String = "${this.variable} ${if (this.fixed) "fixing" else "with"} ${this.rule}"
    }

    private val binds: Collection<Bind> get() = this.fixedBinds + this.variableBinds

    override fun init() = this.nestedRule.init()

    override fun InvocationContext.invoke() {
        frame(*this@BindingVariableRule.variableBinds.toFrameData()) {
            // Applying two different frames allows fixed binds to refer to other variables for context even within
            // the same bind, potentially allowing for more expressiveness
            val fixes = computeFixes(this@BindingVariableRule.fixedBinds)
            frame(*fixes.toFrameData()) {
                append(this@BindingVariableRule.nestedRule)
            }
        }
    }

    // The nested rule stored is already finished, so we do not need to finish it again
    override fun finish(): InvokableGrammarRule = this

    override fun equals(other: Any?): Boolean = this === other || (other is BindingVariableRule && this.binds == other.binds && this.nestedRule == other.nestedRule)

    override fun hashCode(): Int = this.binds.hashCode() * 31 + this.nestedRule.hashCode()
    override fun toString(): String = "bind(${this.binds.joinToString(separator = ", ")}) { ${this.nestedRule} }"

    private fun Collection<Bind>.toFrameData(): Array<out Pair<RuleVariable, InvokableGrammarRule>> = this.map { it.variable to it.rule }.toTypedArray()

    private fun InvocationContext.computeFixes(binds: Collection<Bind>): Collection<Bind> = binds.map { it.copy(rule = computeFixAsLiteral(it.rule)) }

    private fun InvocationContext.computeFixAsLiteral(bind: InvokableGrammarRule): InvokableGrammarRule {
        val builder = StringBuilder()
        val newContext = this.copy(builder = builder)
        newContext.append(bind)
        return LiteralRule(builder.toString())
    }
}
