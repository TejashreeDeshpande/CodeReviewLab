package com.tejashree.codereviewlab.features.leetcode

class CheckForAnagram {
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val count = IntArray(26)

        for (i in s.indices) {
            count[s[i] - 'a']++
            count[s[i] - 'a']--
        }

        return count.all { it == 0 }
    }
}

