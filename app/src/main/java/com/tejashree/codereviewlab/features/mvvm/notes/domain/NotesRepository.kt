package com.tejashree.codereviewlab.features.mvvm.notes.domain

import com.tejashree.codereviewlab.features.mvvm.notes.data.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotesFlow(): Flow<List<Note>>

    suspend fun getNoteById(id: String): Note?
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}