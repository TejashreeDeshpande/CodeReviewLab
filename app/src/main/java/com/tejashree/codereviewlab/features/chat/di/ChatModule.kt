package com.tejashree.codereviewlab.features.chat.di

import androidx.room.Room
import com.tejashree.codereviewlab.features.chat.data.ChatRepository
import com.tejashree.codereviewlab.features.chat.data.ChatViewModel
import com.tejashree.codereviewlab.features.chat.data.MessageDao
import com.tejashree.codereviewlab.features.chat.data.local.ChatDatabase
import com.tejashree.codereviewlab.features.chat.data.remote.ChatApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val chatModule = module {

    single<Retrofit> {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://s3.amazonaws.com/")
            .client(get())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single<ChatDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ChatDatabase::class.java,
            "chat_database"
        ).build()
    }

    single<MessageDao> { get<ChatDatabase>().messageDao() }
    single<ChatApiService> { get<Retrofit>().create(ChatApiService::class.java) }

    single<ChatRepository> {
        ChatRepository(
            messageDao = get(),
            apiService = get()
        )
    }

    viewModel { ChatViewModel(conversationId = "default_conv", repository = get()) }
}
