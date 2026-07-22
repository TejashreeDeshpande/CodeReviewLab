package com.tejashree.codereviewlab.features.chat.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.chat.data.local.MessageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ChatUiIntent {
    data class SendTextMessage(val text: String) : ChatUiIntent
    data class RetryMessage(val message: MessageEntity) : ChatUiIntent
}

data class ChatUiState(
    val messages: List<MessageEntity> = emptyList(),
    val currentUserId: String = "user_me"
)

class ChatViewModel(
    private val conversationId: String,
    val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState

    init {
        // Continuous SSOT observation from Room
        repository.getMessages(conversationId)
            .onEach { messageList ->
                _uiState.update { it.copy(messages = messageList) }
            }
            .launchIn(viewModelScope)
    }

    fun processIntent(intent: ChatUiIntent) {
        viewModelScope.launch {
            when (intent) {
                is ChatUiIntent.SendTextMessage -> {
                    if (intent.text.isNotBlank()) {
                        repository.sendMessage(
                            conversationId = conversationId,
                            senderId = _uiState.value.currentUserId,
                            text = intent.text
                        )
                    }
                }

                is ChatUiIntent.RetryMessage -> {
                    repository.syncMessage(intent.message)
                }
            }
        }
    }

}