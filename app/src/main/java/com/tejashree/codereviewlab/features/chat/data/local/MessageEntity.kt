package com.tejashree.codereviewlab.features.chat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MessageStatus { PENDING, SENT, FAILED }

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val localId: String, // UUID generated on client
    val sequenceId: Long? = null, // Global ordering ID assigned
    val conversationId: String,
    val senderId: String,
    val text: String,
    val clientTimestamp: Long, // Local creation time for sorting PENDING items
    val serverTimestamp: Long? = null, // Official server timestamp
    val status: MessageStatus = MessageStatus.PENDING
)