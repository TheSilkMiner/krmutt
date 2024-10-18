package net.thesilkminer.krmutt.kts.lang

interface NamedObjectCollection<out T : Any> {
    val objects: Map<String, NamedObjectProvider<T>>

    fun named(name: String): NamedObjectProvider<T>

    operator fun get(name: String): NamedObjectProvider<T> = this.named(name)
}
