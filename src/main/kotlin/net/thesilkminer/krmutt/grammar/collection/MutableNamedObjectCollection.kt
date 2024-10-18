package net.thesilkminer.krmutt.grammar.collection

import net.thesilkminer.krmutt.kts.lang.NamedObjectCollection
import net.thesilkminer.krmutt.kts.lang.NamedObjectProvider
import net.thesilkminer.krmutt.kts.lang.getOrThrow
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

internal class MutableNamedObjectCollection<T : Any>(private val providerCreator: (NamedObjectCollection<T>, String) -> MutableNamedObjectProvider<T>) : NamedObjectCollection<T> {
    private val backingMap = mutableMapOf<String, MutableNamedObjectProvider<T>>()
    private val lock = ReentrantReadWriteLock()

    override val objects: Map<String, NamedObjectProvider<T>> get() = this.backingMap.toMap()
    override fun named(name: String): NamedObjectProvider<T> = this.lock.readLock().withLock { this.ref(name) }

    operator fun set(name: String, `object`: T): NamedObjectProvider<T> {
        return this.lock.writeLock().withLock {
            val ref = this.ref(name)
            ref.set(`object`)
            ref
        }
    }

    private fun ref(name: String): MutableNamedObjectProvider<T> = this.backingMap.getOrPut(name) { this.providerCreator(this, name) }
}
