package com.tejashree.codereviewlab.features.leetcode

class PrefixCount {
    fun prefixCount(
        words: Array<String>,
        pref: String
    ): Int {
        var count = 0
        for (word in words) {
            if (word.startsWith(pref)) {
                count++
            }
        }
        return count
    }
}
// O(n * m)
// O(1)