package ru.dvn.dilib

interface Dependency {
    fun get(): Any
}

class Singleton(private val initializer: () -> Any) : Dependency {
    private val value: Any by lazy { initializer.invoke() }

    override fun get(): Any {
        return value
    }
}

class Factory(private val initializer: () -> Any) : Dependency {
    override fun get(): Any {
        return initializer.invoke()
    }
}