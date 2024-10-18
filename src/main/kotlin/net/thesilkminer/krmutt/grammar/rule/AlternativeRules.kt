@file:JvmName("AlternativeRules")

package net.thesilkminer.krmutt.grammar.rule

import net.thesilkminer.krmutt.kts.lang.building.AlternativeGrammarRule
import net.thesilkminer.krmutt.kts.lang.building.DeterministicAlternativeGrammarRule
import kotlin.math.roundToInt
import kotlin.random.Random

internal sealed interface AlternativeDataWeightSpec

internal data class AlternativePercentageSpec(val percentage: Double) : AlternativeDataWeightSpec {
    override fun toString(): String = "% ${this.percentage}"
}

internal data class AlternativeWeightSpec(val weight: Int): AlternativeDataWeightSpec {
    override fun toString(): String = "* ${this.weight}"
}

internal data class AlternativeSpec(val rule: InvokableGrammarRule, val spec: AlternativeDataWeightSpec) {
    internal val percentage: Double get() = (this.spec as? AlternativePercentageSpec ?: error("Rule not yet prepared")).percentage

    override fun toString(): String = "(${this.rule} ${this.spec})"
}

internal abstract class AlternativeRule protected constructor(protected val alternatives: List<AlternativeSpec>) : InvokableGrammarRule, AlternativeGrammarRule {
    private data class RuleScanResult(
        val percentageAlternatives: List<AlternativeSpec?>,
        val weightedAlternatives: List<AlternativeSpec?>,
        val totalPercentage: Double,
        val totalWeight: Int
    )

    final override fun init() {
        this.alternatives.forEach { it.rule.init() }
    }

    abstract fun or(spec: AlternativeSpec): AlternativeGrammarRule

    override fun equals(other: Any?): Boolean = this === other || (other is AlternativeRule && this.alternatives == other.alternatives)
    override fun hashCode(): Int = this.alternatives.hashCode()
    override fun toString(): String = this.alternatives.joinToString(separator = " or ")

    protected fun probabilityDistribution(): List<AlternativeSpec> {
        val (percentageAlternatives, weightedAlternatives, totalPercentage, totalWeight) = this.scanAlternatives()
        check(totalPercentage in 0.0..1.0) { "Percentage distribution is invalid as total is $totalPercentage and outside bounds" }

        if (((totalPercentage * 100).roundToInt() / 100) == 1) {
            check(totalWeight == 0) { "Percentage entries already form a full probability distribution: cannot have weighted entries too" }
        }

        val remainingPercentage = 1.0 - totalPercentage
        val perWeightPercentage = remainingPercentage / totalWeight.toDouble()

        val resultingList = mutableListOf<AlternativeSpec>()
        percentageAlternatives.forEachIndexed { i, spec ->
            if (spec != null) {
                resultingList += spec
            } else {
                val weightedSpec = weightedAlternatives[i]!!
                val associatedPercentage = perWeightPercentage * (weightedSpec.spec as AlternativeWeightSpec).weight
                resultingList += weightedSpec.copy(spec = AlternativePercentageSpec(associatedPercentage))
            }
        }
        return resultingList.toList()
    }

    private fun scanAlternatives(): RuleScanResult {
        val percentageAlternatives = mutableListOf<AlternativeSpec?>()
        val weightedAlternatives = mutableListOf<AlternativeSpec?>()
        var totalPercentage = 0.0
        var totalWeight = 0

        this.alternatives.forEach {
            val finishedSpec = it.copy(rule = it.rule.finish())
            when (val weightSpec = finishedSpec.spec) {
                is AlternativePercentageSpec -> {
                    percentageAlternatives += finishedSpec
                    weightedAlternatives += null
                    totalPercentage += weightSpec.percentage
                }
                is AlternativeWeightSpec -> {
                    percentageAlternatives += null
                    weightedAlternatives += finishedSpec
                    totalWeight += weightSpec.weight
                }
            }
        }

        assert(run {
            val size = percentageAlternatives.count()
            for (i in 0 until size) {
                val percentageSpec = percentageAlternatives[i]
                val weightSpec = weightedAlternatives[i]
                if (percentageSpec == null && weightSpec == null) {
                    return@run false
                }
                if (percentageSpec != null && weightSpec != null) {
                    return@run false
                }
            }
            return@run true
        })

        return RuleScanResult(percentageAlternatives.toList(), weightedAlternatives.toList(), totalPercentage, totalWeight)
    }

    // Shit name on purpose as this is not supposed to be accessible at all: you really have to fight autocompletion to get here
    @Suppress("FunctionName")
    @JvmSynthetic
    internal fun `  ___alt___  `(): List<AlternativeSpec> = this.alternatives
}

internal class RandomAlternativeRule(alternatives: List<AlternativeSpec>) : AlternativeRule(alternatives) {
    constructor(alternative: AlternativeSpec) : this(listOf(alternative))

    override fun InvocationContext.invoke() {
        // When this method is invoked, it is guaranteed that the list is a probability distribution
        var target = randomly(Random::nextDouble)

        for (alternative in this@RandomAlternativeRule.alternatives) {
            target -= alternative.percentage
            if (target <= 0.0) {
                append(alternative.rule)
            }
            break
        }

        assert(target <= 0.0)
    }

    override fun finish(): InvokableGrammarRule = RandomAlternativeRule(this.probabilityDistribution())

    override fun or(spec: AlternativeSpec): AlternativeGrammarRule = RandomAlternativeRule(this.alternatives + spec)
}

internal class DeterministicAlternativeRule(
    alternatives: List<AlternativeSpec>,
    private val shuffleMode: ShuffleMode
) : AlternativeRule(alternatives), DeterministicAlternativeGrammarRule {
    constructor(other: AlternativeRule, shuffleMode: ShuffleMode) : this(other.`  ___alt___  `(), shuffleMode)
    constructor(other: AlternativeRule) : this(other, ShuffleMode.NONE)

    internal enum class ShuffleMode(private val toString: String) {
        NONE(""),
        ONCE(".shuffled"),
        ALWAYS(".shuffling()");

        override fun toString(): String = this.toString
    }

    private class CircularInt(private var value: Int, private val max: Int) {
        constructor(max: Int) : this(0, max)

        @Suppress("FunctionName")
        fun `++`(): Int {
            val currentValue = this.value
            this.value = (++this.value % this.max)
            return currentValue
        }

        override fun equals(other: Any?): Boolean = (other is Int && this.value == other) || (other is CircularInt && this.value == other.value)
        override fun hashCode(): Int = this.value.hashCode()
        override fun toString(): String = this.value.toString()
    }

    private val circularInt: CircularInt = CircularInt(this.alternatives.count())
    private val shuffledList: MutableList<AlternativeSpec> = mutableListOf()

    override fun InvocationContext.invoke() {
        append(this@DeterministicAlternativeRule.currentRule())
    }

    override fun finish(): InvokableGrammarRule = DeterministicAlternativeRule(this.probabilityDistribution(), this.shuffleMode)

    override fun or(spec: AlternativeSpec): DeterministicAlternativeGrammarRule = DeterministicAlternativeRule(this.alternatives + spec, this.shuffleMode)

    override fun toString(): String = "(${super.toString()}).det${this.shuffleMode}"

    private fun currentRule(): InvokableGrammarRule {
        val current = this.circularInt.`++`()
        if (current == 0) {
            this.shuffle()
        }

        val spec = this.shuffledList[current]
        return spec.rule
    }

    private fun shuffle() {
        val emptyInitialChoice = this.shuffledList.isEmpty()
        val shallShuffle = this.shuffleMode == ShuffleMode.ALWAYS || (this.shuffleMode == ShuffleMode.ONCE && emptyInitialChoice)

        this.shuffledList.clear()
        this.shuffledList += this.alternatives.let { if (shallShuffle) it.shuffled() else it }
    }
}
