package com.tejashree.codereviewlab.features.leetcode

class LongestCommonPrefix {
    fun longestCommonPrefix(strs: Array<String>): String {

        if (strs.isEmpty()) return ""

        var prefix = strs[0]
        for (i in 1 until strs.size) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.dropLast(1)
            }
        }
        return prefix
    }
}