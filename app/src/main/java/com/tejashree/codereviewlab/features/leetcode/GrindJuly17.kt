package com.tejashree.codereviewlab.features.leetcode

class GrindJuly17 {

    fun isSubSequence(sub: String, str: String): Boolean {
        var count = 0
        for (ch in str) {
            if (count < sub.length && ch == sub[count]) {
                count++
            }
        }
        return count == sub.length
    }

    fun longestCommonPrefix(strs: List<String>): String {
        if (strs.isEmpty()) return ""
        var prefix = strs[0]

        for (s in strs) {
            while (s.indexOf(prefix) != 0) {
                prefix = prefix.dropLast(1)
            }
        }
        return prefix
    }

    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val count = IntArray(26)

        for (i in s.indices) {
            count[s[i] - 'a']++
            count[t[i] - 'a']--
        }
        return count.all { it == 0 }
    }

    class SimpleStack<T> {
        private val items = mutableListOf<T>()
        fun push(item: T) {
            items.add(item)
        }
        fun pop(): T? {
            return if (items.isEmpty()) null else items.removeAt(items.lastIndex)
        }
        fun peek(): T? {
            return items.lastOrNull()
        }
        fun isEmpty(): Boolean {
            return items.isEmpty()
        }
    }

    fun reverseStrUsingStack(input: String): String {
        val stack = ArrayDeque<Char>()
        for (char in input) {
            stack.addLast(char)
        }
        val result = StringBuffer()
        while (stack.isNotEmpty()) {
            result.append(stack.removeLast())
        }
        return result.toString()
    }

    fun isValidParentheses(input: String): Boolean {
        val stack = ArrayDeque<Char>()

        for (c in input) {
            when (c) {
                '(' -> stack.addLast(')')
                '[' -> stack.addLast(']')
                '{' -> stack.addLast('}')
                else -> {
                    if (stack.isEmpty() || stack.removeLast() != c) {
                        return false
                    }
                }
            }
        }
        return stack.isEmpty()
    }

    fun moveZeros(nums: IntArray) {
        var index = 0
        for (n in nums) {
            if (n != 0) {
                nums[index++] = n
            }
        }
        while (index < nums.size) {
            nums[index++] = 0
        }
    }

    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.lastIndex

        while (left <= right) {
            val mid = left + (right-left) /2

            when {
                nums[mid] == target -> return mid
                nums[mid] < target -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return -1
    }

    fun isPalindromeNumber(x: Int): Boolean {
        if (x < 0) return false

        var original = x
        var reversed = 0

        while (original != 0) {
            reversed = reversed * 10 + original % 10
            original /= 10
        }
        return reversed == x
    }

    fun isPalindromeString(s: String): Boolean {
        var left = 0
        var right = s.lastIndex

        while (left < right) {
            while (left < right && !s[left].isLetterOrDigit())
                left++

            while (left < right && !s[right].isLetterOrDigit())
                right--

            if (s[left].lowercaseChar() != s[right].lowercaseChar())
                return false

            left++
            right--
        }
        return true
    }
    fun removeAdjacentDuplicates(input: String): String {
        val stack = ArrayDeque<Char>()

        for (char in input) {
            if (stack.lastOrNull() == char) {
                stack.removeLast()
            } else {
                stack.addLast(char)
            }
        }
        return stack.joinToString("")
    }

    class MovingAverage(
        private val windowSize: Int
    ) {
        private val queue = ArrayDeque<Int>()
        private var sum = 0.0

        fun next(value: Int): Double {
            queue.addLast(value)
            sum += value

            if (queue.size > windowSize) {
                sum -= queue.removeFirst()
            }
            return sum / queue.size
        }
    }
}