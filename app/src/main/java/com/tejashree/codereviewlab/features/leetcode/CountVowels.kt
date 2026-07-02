package com.tejashree.codereviewlab.features.leetcode

class CountVowels {
    fun countVowels(str: String): Int {
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        var count = 0

        for (ch in str.lowercase()) {
            if (ch in vowels) count++
        }
        return count
    }
}