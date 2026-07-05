package com.tejashree.codereviewlab.features.mvvm.notes.di

import com.tejashree.codereviewlab.features.mvvm.notes.data.MockNotesRepository
import com.tejashree.codereviewlab.features.mvvm.notes.domain.NotesRepository
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NoteDetailsViewModel
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NotesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notesAppModule = module {
    singleOf(::MockNotesRepository) bind NotesRepository::class
    viewModelOf(::NotesViewModel)
    viewModelOf(::NoteDetailsViewModel)
}