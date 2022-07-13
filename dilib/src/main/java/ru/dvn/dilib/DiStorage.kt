package ru.dvn.dilib

import kotlin.reflect.KClass

object DiStorage {
    private val store = HashMap<KClass<*>, Dependency<*>>()

    fun <T: Any> get(clazz: KClass<T>): T {
        if (!store.containsKey(clazz)) {
            throw IllegalArgumentException("Unknown Type ${clazz::class}")
        }

        val dependency = store[clazz] ?: throw NullPointerException("Dependency is null")

        return dependency.get() as T
    }

    fun <T: Any> add(key: KClass<T>, dependency: Dependency<T>) {
        store[key] = dependency
    }

    inline fun<reified T: Any> add(dependency: Dependency<T>) {
        add(T::class, dependency)
    }
}

inline fun<reified T: Any> get(): T {
    return DiStorage.get(T::class)
}

inline fun<reified T: Any> inject() = lazy { get<T>() }