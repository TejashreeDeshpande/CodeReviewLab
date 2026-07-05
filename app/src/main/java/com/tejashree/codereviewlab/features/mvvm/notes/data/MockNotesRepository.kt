package com.tejashree.codereviewlab.features.mvvm.notes.data

import com.tejashree.codereviewlab.features.mvvm.notes.domain.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockNotesRepository : NotesRepository {
    private val notes = mutableListOf(
        Note(
            id = "1",
            desc = "Buy groceries",
            lastUpdatedTimeStamp = System.currentTimeMillis()
        ),
        Note(
            id = "2",
            desc = "Finish Android project",
            lastUpdatedTimeStamp = System.currentTimeMillis() - 60_000
        )
    )

    private val notesFlow = MutableStateFlow(notes.toList())

    override fun getNotesFlow(): Flow<List<Note>> {
        return notesFlow.asStateFlow()
    }

    override suspend fun getNoteById(id: String): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
        notesFlow.value = notes.toList()
    }

    override suspend fun updateNote(note: Note) {
        notes.add(note)
        notesFlow.value = notes.toList()
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
        notesFlow.value = notes.toList()
    }
}