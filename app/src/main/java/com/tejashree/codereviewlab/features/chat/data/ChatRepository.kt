package com.tejashree.codereviewlab.features.chat.data

import com.tejashree.codereviewlab.features.chat.data.local.MessageEntity
import com.tejashree.codereviewlab.features.chat.data.local.MessageStatus
import com.tejashree.codereviewlab.features.chat.data.remote.ChatApiService
import com.tejashree.codereviewlab.features.chat.data.remote.SendMessageRequest
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ChatRepository(
    private val messageDao: MessageDao,
    private val apiService: ChatApiService
) {

    fun getMessages(conversationId: String): Flow<List<MessageEntity>> {
        return messageDao.observeMessages(conversationId)
    }

    suspend fun sendMessage(
        conversationId: String,
        senderId: String,
        text: String
    ) {

        val localMessage = MessageEntity(
            localId = UUID.randomUUID().toString(),
            conversationId = conversationId,
            senderId = senderId,
            text = text,
            clientTimestamp = System.currentTimeMillis(),
            status = MessageStatus.PENDING
        )
        messageDao.insertOrUpdate(localMessage)

        syncMessage(localMessage)

    }

    suspend fun syncMessage(message: MessageEntity) {
        try {
            val ack = apiService.postMessage(
                request =
                    SendMessageRequest(
                        localId = message.localId,
                        conversationId = message.conversationId,
                        text = message.text
                    )
            )
            messageDao.reconcileMessage(
                localId = ack.localId,
                sequenceId = ack.sequenceId,
                serverTimestamp = ack.serverTimestamp,
                status = MessageStatus.SENT
            )
        } catch (e: Exception) {
            messageDao.markAsFailed(message.localId)
        }
    }

    suspend fun retryPendingMessages() {
        val pending = messageDao.getPendingMessages()
        pending.forEach { syncMessage(it) }
    }

}