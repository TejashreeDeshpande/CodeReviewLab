package com.tejashree.codereviewlab.features.mvvm.notes.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.features.mvvm.notes.data.Note
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NotesViewModel
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun PreviewNotesListScreen() {
    val sampleNotes = persistentListOf(
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
    CodeReviewLabTheme {
        NotesListScreenContents(notes = sampleNotes, onClickNote = {})
    }
}

@Composable
fun NotesListScreen(
    viewModel: NotesViewModel = koinViewModel(),
    onNoteClick: (String) -> Unit = {},
    onAddNoteClick: () -> Unit = {},
    onBack: () -> Unit
) {
    val state by viewModel.notes.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { AppTopBar(title = "Notes", onBack = onBack) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { innerPadding ->
        NotesListScreenContents(
            notes = state,
            onClickNote = { onNoteClick(it.id) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NotesListScreenContents(
    notes: PersistentList<Note>,
    onClickNote: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = notes,
            key = { note -> note.id }
        ) { note ->
            ListItem(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onClickNote(note) },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                headlineContent = {
                    Text(
                        text = note.title
                    )
                },
                supportingContent = {
                    Text(
                        text = note.lastUpdatedDate,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            )
        }
    }
}