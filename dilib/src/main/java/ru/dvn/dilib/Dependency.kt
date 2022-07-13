package ru.dvn.dilib

interface Dependency<T> {
    fun get(): Any
}

class Singleton<T: Any>(private val initializer: () -> Any) : Dependency<T> {
    private val value: Any by lazy { initializer.invoke() }

    override fun get(): Any {
        return value
    }
}

class Factory<T: Any>(private val initializer: () -> Any) : Dependency<T> {
    override fun get(): Any {
        return initializer.invoke()
    }
}

fun <T: Any> singleton(initializer: () -> T): Dependency<T> {
    return Singleton(initializer)
}

fun <T: Any> factory(initializer: () -> T): Dependency<T> {
    return Factory(initializer)
}