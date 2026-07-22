package com.tejashree.codereviewlab.features.chat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tejashree.codereviewlab.features.chat.data.local.MessageEntity
import com.tejashree.codereviewlab.features.chat.data.local.MessageStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    // SSOT: Order SENT/CONFIRMED items by server sequenceId, PENDING items fall back to clientTimestamp
    @Query(
        """
        SELECT * FROM messages 
        WHERE conversationId = :conversationId 
        ORDER BY 
            CASE WHEN sequenceId IS NOT NULL THEN sequenceId ELSE 0 END ASC,
            clientTimestamp ASC
    """
    )
    fun observeMessages(conversationId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE status = 'PENDING'")
    suspend fun getPendingMessages(): List<MessageEntity>

    // Reconciliation Update
    @Query("""
        UPDATE messages 
        SET sequenceId = :sequenceId, 
            serverTimestamp = :serverTimestamp, 
            status = :status
        WHERE localId = :localId
    """)
    suspend fun reconcileMessage(
        localId: String,
        sequenceId: Long,
        serverTimestamp: Long,
        status: MessageStatus
    )

    @Query("UPDATE messages SET status = 'FAILED' WHERE localId = :localId")
    suspend fun markAsFailed(localId: String)
}