package ru.dvn.dilib

import kotlin.reflect.KClass

class DiStorage {
    private val store = HashMap<KClass<*>, Dependency>()

    fun <T: Any> get(clazz: KClass<T>): T {
        if (!store.containsKey(clazz)) {
            throw IllegalArgumentException("Unknown Type ${clazz::class}")
        }

        val dependency = store[clazz] ?: throw NullPointerException("Dependency is null")

        return dependency.get() as T
    }

    fun <T: Any> add(key: KClass<T>, dependency: Dependency) {
        store[key] = dependency
    }
}