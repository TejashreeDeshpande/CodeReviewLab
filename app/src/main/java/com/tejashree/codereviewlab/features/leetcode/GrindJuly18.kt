package com.tejashree.codereviewlab.features.leetcode

class GrindJuly18 {

    fun isPalindromeString(s: String): Boolean {
        var left = 0
        var right = s.lastIndex

        while (left < right) {
            while (left < right && !s[left].isLetterOrDigit()) {
                left++
            }
            while (left < right && !s[right].isLetterOrDigit()) {
                right--
            }
            if (s[left].lowercaseChar() != s[right].lowercaseChar())
                return false

            left++
            right--
        }
        return true
    }

    fun validPalindrome(s: String): Boolean {
        fun check(left: Int, right: Int): Boolean {
            var l = left
            var r = right

            while (l < r) {
                if (s[l] != s[r])
                    return false
                l++
                r--
            }
            return true
        }

        var left = 0
        var right = s.lastIndex
        while (left < right) {
            if (s[left] != s[right]) {
                return check(left + 1, right) || check(left, right - 1)
            }
            left++
            right--
        }
        return true
    }

    fun containsDuplicates(nums: IntArray): Boolean {
        val set = HashSet<Int>()
        for (n in nums) {
            if (!set.add(n)) {
                return true
            }
        }
        return false
    }

    fun validAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val count = IntArray(26)

        for (i in s.indices) {
            count[s[i] - 'a']++
            count[t[i] - 'a']--
        }
        return count.all { it == 0 }
    }

    data class ListNode(
        var value: Int,
        var next: ListNode? = null
    )

    fun mergeSortedList(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2

        while (p1 != null && p2 != null) {
            if (p1.value < p2.value) {
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current.next!!
        }
        current.next = p1 ?: p2

        return dummy.next
    }

}