package com.tejashree.codereviewlab.features.leetcode

class GrindJuly16 {
    // SubSequence sub = "ace" str = "abcde"
    fun isSubSequence(sub: String, str: String): Boolean {
        var count = 0
        for (c in str) {
            if (count < sub.length && sub[count] == c)
                count++
        }
        return (count == sub.length)
    }

    // LongestCommonPrefix
    fun longestCommonPrefix(strs: List<String>): String {
        if (strs.isEmpty()) return ""

        var prefix = strs[0]

        for (str in strs) {
            while (str.indexOf(prefix) != 0) {
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
        return count.any { it == 0 }
    }

    // Number of Islands
    fun numIslands(grid: Array<CharArray>): Int {

        var count = 0

        fun dfs(r: Int, c: Int) {

            if (r !in grid.indices ||
                c !in grid[0].indices ||
                grid[r][c] == '0'
            )
                return

            grid[r][c] = '0'

            dfs(r + 1, c)
            dfs(r - 1, c)
            dfs(r, c + 1)
            dfs(r, c - 1)
        }

        for (r in grid.indices) {

            for (c in grid[0].indices) {

                if (grid[r][c] == '1') {

                    count++

                    dfs(r, c)
                }
            }
        }

        return count
    }

    //----------------------------------------------------------------------//
    // Stack : Last In, First Out
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

    // Reverse string using Stack
    fun reverseStr(input: String): String {
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
                    if (stack.isEmpty() || stack.removeLast() != c)
                        return false
                }
            }
        }
        return stack.isEmpty()
    }

    //----------------------------------------------------------------------//
    // Sliding Window
    // Best Time to Buy and Sell Stock
    fun maxProfit(prices: IntArray): Int {
        var minPrice = Int.MAX_VALUE
        var profit = 0

        for (price in prices) {
            minPrice = minOf(minPrice, price)
            profit = maxOf(profit, price - minPrice)
        }

        return profit
    }

    // maxSum
    fun maxSum(nums: IntArray, k: Int): Int {
        var windowSum = 0

        for (i in 0 until k)
            windowSum += nums[i]

        var max = windowSum

        for (i in k until nums.size) {
            windowSum += nums[i]
            windowSum -= nums[i - k]
            max = maxOf(max, windowSum)
        }

        return max
    }

    // lengthOfLongestSubstring
    fun lengthOfLongestSubstring(s: String): Int {
        val map = HashMap<Char, Int>()

        var left = 0
        var ans = 0

        for (right in s.indices) {

            val c = s[right]

            if (map.containsKey(c))
                left = maxOf(left, map[c]!! + 1)

            map[c] = right

            ans = maxOf(ans, right - left + 1)
        }

        return ans
    }

    // minSubArrayLen
    fun minSubArrayLen(target: Int, nums: IntArray): Int {

        var left = 0
        var sum = 0
        var ans = Int.MAX_VALUE

        for (right in nums.indices) {

            sum += nums[right]

            while (sum >= target) {

                ans = minOf(ans, right - left + 1)

                sum -= nums[left]
                left++
            }
        }

        return if (ans == Int.MAX_VALUE) 0 else ans
    }

    // Move Zeroes
    fun moveZeroes(nums: IntArray) {
        var index = 0

        for (num in nums) {
            if (num != 0)
                nums[index++] = num
        }

        while (index < nums.size)
            nums[index++] = 0
    }

    // Binary search
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.lastIndex

        while (left <= right) {
            val mid = left + (right - left) / 2

            when {
                nums[mid] == target -> return mid
                nums[mid] < target -> left = mid + 1
                else -> right = mid - 1
            }
        }

        return -1
    }

    // Palindrome number
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

    // Palindrome string
    // Input: "A man, a plan, a canal: Panama"
    // Output: true
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

    fun firstUniqueChar(s: String): Int {
        val count = IntArray(26)

        for (c in s)
            count[c - 'a']++

        for (i in s.indices)
            if (count[s[i] - 'a'] == 1)
                return i

        return -1
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

    //----------------------------------------------------------------------//
    // LinkedList
    // ListNode
    data class ListNode(
        val value: Int,
        var next: ListNode? = null
    )

    fun mergeLinkedList(l1: ListNode?, l2: ListNode?): ListNode? {
        var p1 = l1
        var p2 = l2
        val dummy = ListNode(0)
        var current = dummy

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
        current.next = l1 ?: l2
        return dummy.next
    }

    fun findMiddleOfLinkedList(head: ListNode?): ListNode? {
        var slow = head
        var fast = head

        while (fast?.next?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }

        return slow?.next
    }

    // 1 -> 2 -> 3 -> 4
    fun reverseOfLinkedList(head: ListNode?): ListNode? {
        var current = head
        var prev: ListNode? = null

        while (current != null) {
            val next = current.next
            current.next = prev
            prev = current
            current = next
        }
        return prev
    }

    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head

        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) return true
        }
        return false
    }

    class SinglyLinkedList {
        private var head: ListNode? = null

        // Insert at the beginning
        // Insert at beginning
        fun insertAtBeginning(value: Int) {
            val newNode = ListNode(value)
            newNode.next = head
            head = newNode
        }

        // Insert at end
        fun insertAtEnd(value: Int) {
            val newNode = ListNode(value)
            if (head == null) {
                head = newNode
                return
            }
            var current = head
            while (current?.next != null) {
                current = current.next
            }
            current?.next = newNode
        }

        // delete first occurrence
        fun delete(value: Int) {
            if (head == null) return
            if (head?.value == value) {
                head = head?.next
                return
            }
            var current = head
            while (current?.next != null) {

                if (current.next?.value == value) {
                    current.next = current.next?.next
                    return
                }
                current = current.next
            }
        }

        // Search
        fun contains(value: Int): Boolean {
            var current = head
            while (current != null) {
                if (current.value == value) {
                    return true
                }
                current = current.next
            }
            return false
        }

        // Reverse
        fun reverse() {
            var current = head
            var prev: ListNode? = null
            while (current != null) {
                val next = current.next
                current.next = prev
                prev = current
                current = next
            }
            head = prev
        }

    }

    //----------------------------------------------------------------------//
    // Tree
    data class TreeNode(
        val value: Int,
        var left: TreeNode? = null,
        var right: TreeNode? = null
    )

    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + maxOf(
            maxDepth(root.left),
            maxDepth(root.right)
        )
    }

    //----------------------------------------------------------------------//
    // Graph
    val graph = mapOf(
        0 to listOf(1, 3),
        1 to listOf(2),
        2 to emptyList(),
        3 to emptyList()
    )

    fun dfs(node: Int, visited: MutableSet<Int>) {
        if (node in visited) return

        visited.add(node)
        println(node)

        graph[node]?.forEach {
            dfs(it, visited)
        }
    }

    fun bfs(start: Int) {

        val queue = ArrayDeque<Int>()
        val visited = mutableSetOf<Int>()

        queue.add(start)
        visited.add(start)

        while (queue.isNotEmpty()) {

            val node = queue.removeFirst()

            println(node)

            graph[node]?.forEach {

                if (it !in visited) {
                    visited.add(it)
                    queue.add(it)
                }
            }
        }
    }


}
