package com.tejashree.codereviewlab.features.chat.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

// Request DTO sent to backend
data class SendMessageRequest(
    val localId: String,
    val conversationId: String,
    val text: String
)

// Response DTO (Server Reconciliation Acknowledgement)
data class MessageAckResponse(
    val localId: String,
    val sequenceId: Long,        // Server's global auto-incrementing order ID
    val serverTimestamp: Long    // Official server time authority
)

interface ChatApiService {
    @POST("v1/chat/messages")
    suspend fun postMessage(
        @Body request: SendMessageRequest
    ): MessageAckResponse
}