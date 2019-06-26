package com.sunil.kumar.data.cache

interface EntityMapper<T, V> {

    fun mapFromCached(type: V): T

    fun mapToCached(type: T): V

}