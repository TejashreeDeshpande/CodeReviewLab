package com.tejashree.codereviewlab.features.chat.data.remote

import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration.Companion.milliseconds

class FakeChatApiService(
    private val simulatedDelayMs: Long = 800L,
    private val shouldFail: Boolean = false
) : ChatApiService {

    // Simulates the server's global auto-incrementing sequence counter
    private val globalSequenceCounter = AtomicLong(1000L)

    override suspend fun postMessage(request: SendMessageRequest): MessageAckResponse {
        // Simulate network latency
        delay(simulatedDelayMs.milliseconds)

        if (shouldFail) {
            throw java.io.IOException("Network unreachable / Server 500 error")
        }

        return MessageAckResponse(
            localId = request.localId,
            sequenceId = globalSequenceCounter.incrementAndGet(),
            serverTimestamp = System.currentTimeMillis()
        )
    }
}