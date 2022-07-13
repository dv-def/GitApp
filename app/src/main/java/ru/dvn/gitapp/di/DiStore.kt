package ru.dvn.gitapp.di

import kotlin.reflect.KClass

class DiStore {
    private val store = HashMap<KClass<*>, Any>()

    fun <T: Any> get(clazz: KClass<T>): T {
        if (!store.containsKey(clazz)) {
            throw IllegalArgumentException("Unknown Type ${clazz::class}")
        }

        return store[clazz] as T
    }

    fun <T: Any> add(key: KClass<T>, dependency: Any) {
        store[key] = dependency
    }
}