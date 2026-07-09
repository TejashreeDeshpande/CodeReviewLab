package com.tejashree.codereviewlab.features.leetcode

data class ListNode(
    var value: Int,
    var next: ListNode? = null
)

class LinkedList {
    fun reverseList(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var current: ListNode? = head

        while (current != null) {
            val next = current.next
            current?.next = prev
            prev = current
            current = next
        }
        return prev
    }

}