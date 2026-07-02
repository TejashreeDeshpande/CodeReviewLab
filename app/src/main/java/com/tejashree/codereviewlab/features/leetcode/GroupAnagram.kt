package com.tejashree.codereviewlab.features.leetcode

class GroupAnagram {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()

        for (word in strs) {
            val count = IntArray(26)

            for (ch in word) {
                count[ch - 'a']++
            }
            val key = count.joinToString("#")
            map.getOrPut(key) { mutableListOf() }.add(word)
        }
        return map.values.toList()
    }
}