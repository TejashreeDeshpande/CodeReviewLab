package com.tejashree.codereviewlab.features.mvvm.notes.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NoteRoute : NavKey {
    @Serializable
    data object NoteList : NoteRoute

    @Serializable
    data class NoteDetail(val noteId: String? = null) : NoteRoute
}