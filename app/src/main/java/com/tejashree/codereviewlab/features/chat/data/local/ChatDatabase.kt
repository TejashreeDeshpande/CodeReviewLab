package com.tejashree.codereviewlab.features.chat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tejashree.codereviewlab.features.chat.data.MessageDao

@Database(entities = [MessageEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
