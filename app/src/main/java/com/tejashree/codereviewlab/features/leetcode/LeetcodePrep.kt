package com.tejashree.codereviewlab.features.leetcode

class LeetcodePrep {

    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        for (i in numbers.indices) {
            val diff = target - numbers[i]
            if (map.containsKey(diff)) {
                return intArrayOf(i, map[diff]!!)
            }
            map[numbers[i]] = i
        }
        return intArrayOf()
    }

    fun anagram(s:String, t:String): Boolean {
        if (s.length != t.length) return false
        val count = IntArray(26)

        for (i in s.indices) {
            count[s[i] - 'a']++
            count[t[i] - 'a']--
        }
        return count.all { it == 0 }
    }

    data class Player(val name: String, val score: Int, val timestamp: Long)

    fun topScorer() {
        val players = listOf(
            Player("Alice", 100, 1690000000L),
            Player("Bob", 150, 1690000010L),
            Player("Charlie", 150, 1690000005L), // Same score as Bob, earlier timestamp
            Player("Diana", 90, 1690000020L)
        )
        // Create the comparator
        val playerComparator = compareByDescending<Player> { it.score }
            .thenBy { it.timestamp }
        val sortedPlayers = players.sortedWith(playerComparator)

        // Print results
        sortedPlayers.forEach { println(it) }
    }

}
