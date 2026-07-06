package com.tejashree.codereviewlab.features.mvvm.notes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.screens.NoteDetailScreen
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.screens.NotesListScreen
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NoteDetailsViewModel
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NotesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesAppNavHost(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {

    val backStack = rememberNavBackStack(NoteRoute.NoteList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator() // Scopes ViewModels directly to the NavEntry lifecycle
        ),
        entryProvider = entryProvider {
            // Screen 1: Note List
            entry<NoteRoute.NoteList> {
                val viewModel: NotesViewModel = koinViewModel()
                NotesListScreen(
                    viewModel = viewModel,
                    onNoteClick = { noteId ->
                        backStack.add(NoteRoute.NoteDetail(noteId))
                    },
                    onAddNoteClick = {
                        backStack.add(NoteRoute.NoteDetail(noteId = null))
                    },
                    onBack = onBack
                )
            }

            // Screen 2: Note Detail
            entry<NoteRoute.NoteDetail> { key ->
                val viewModel: NoteDetailsViewModel = koinViewModel()
                NoteDetailScreen(
                    viewModel = viewModel,
                    noteId = key.noteId,
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        }
    )
}
