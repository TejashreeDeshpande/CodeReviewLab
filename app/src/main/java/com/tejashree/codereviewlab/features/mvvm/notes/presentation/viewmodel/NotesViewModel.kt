package com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.mvvm.notes.data.Note
import com.tejashree.codereviewlab.features.mvvm.notes.domain.NotesRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NotesRepository
) : ViewModel() {
    private val _notes = MutableStateFlow<PersistentList<Note>>(persistentListOf())
    val notes: StateFlow<PersistentList<Note>> = _notes

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            repository.getNotesFlow().collect { notes ->
                _notes.update { notes.toPersistentList() }
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}