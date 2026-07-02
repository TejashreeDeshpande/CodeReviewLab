package com.tejashree.codereviewlab.features.leetcode

class LRUCache<K, V>(private val capacity: Int) {
    private val cache = object: LinkedHashMap<K, V>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>): Boolean {
            return size > capacity
        }
    }
    fun get(key: K): V? = cache[key]
    fun put(key: K, value: V) {
        cache[key] = value
    }
}

