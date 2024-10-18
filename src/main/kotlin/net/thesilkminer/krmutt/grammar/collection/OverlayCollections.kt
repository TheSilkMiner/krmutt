@file:JvmName("OverlayCollections")

package net.thesilkminer.krmutt.grammar.collection

private class OverlayMap<K, out V>(private val base: Map<K, V>, private val overlay: Map<K, V>) : Map<K, V> {
    override val entries: Set<Map.Entry<K, V>> get() = this.base.entries.overlay(this.overlay.entries) { a, b -> a.key == b.key }
    override val keys: Set<K> get() = this.base.keys.overlay(this.overlay.keys) { a, b -> a == b }
    override val size: Int get() = this.keys.count()
    override val values: Collection<V> get() = this.entries.map { it.value }

    override fun containsKey(key: K): Boolean = this.overlay.containsKey(key) || this.base.containsKey(key)
    override fun containsValue(value: @UnsafeVariance V): Boolean = this.overlay.containsValue(value) || this.base.containsValue(value)

    override fun get(key: K): V? {
        val overlayResult = this.overlay[key]
        if (overlayResult == null && !this.overlay.containsKey(key)) {
            return this.base[key]
        }
        return overlayResult
    }

    override fun isEmpty(): Boolean = this.overlay.isEmpty() && this.base.isEmpty()
}

private class OverlaySet<out E>(private val preComputedOverlay: Set<E>) : Set<E> by preComputedOverlay

internal fun <K, V> Map<K, V>.overlay(other: Map<K, V>): Map<K, V> {
    return OverlayMap(this, other)
}

internal fun <E> Set<E>.overlay(other: Set<E>, overlayDeterminer: (E, E) -> Boolean): Set<E> {
    val set = this.toMutableSet()
    this.flatMap { first -> other.map { second -> first to second } }
        .filter { overlayDeterminer(it.first, it.second) }
        .forEach { set.replace(it.first, it.second) }
    return OverlaySet(set.toSet())
}

private fun <E> MutableSet<E>.replace(what: E, with: E) {
    this -= what
    this += with
}
