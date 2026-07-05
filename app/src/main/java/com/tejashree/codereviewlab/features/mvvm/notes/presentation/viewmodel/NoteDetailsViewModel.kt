package com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.mvvm.notes.data.MockNotesRepository
import com.tejashree.codereviewlab.features.mvvm.notes.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    private val repository: MockNotesRepository
) : ViewModel() {

    private val _noteDesc = MutableStateFlow("")
    val noteDesc = _noteDesc.asStateFlow()
    private var currentNoteId: String? = null

    fun getNoteDetails(noteId: String) {
        viewModelScope.launch {
            val note = repository.getNoteById(noteId)
            note?.let {
                _noteDesc.value = it.desc
                currentNoteId = it.id
            }
        }
    }

    fun onDescChange(newDesc: String) {
        _noteDesc.value = newDesc
    }

    fun saveNote() {
        viewModelScope.launch {
            currentNoteId?.let { id ->
                repository.updateNote(
                    Note(
                        id = id,
                        desc = _noteDesc.value,
                        lastUpdatedTimeStamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }
}
