package com.tejashree.codereviewlab.features.leetcode

class IsSubsequence {
    fun isSubSequence(subStr: String, str: String): Boolean {
        var i = 0
        for (ch in str) {
            if (i < subStr.length && ch == subStr[i]) {
                i += 1
            }
        }
        return (i == subStr.length)
    }
}