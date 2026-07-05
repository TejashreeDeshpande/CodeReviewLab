package com.tejashree.codereviewlab.features.mvvm.notes.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.features.mvvm.notes.presentation.viewmodel.NoteDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailsViewModel = koinViewModel(),
    noteId: String?,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (noteId != null) {
            viewModel.getNoteDetails(noteId)
        }
    }
    val noteDesc by viewModel.noteDesc.collectAsState()
    Scaffold(
        topBar = {
            AppTopBar(
                title = if (noteId == null) "Add Note" else "Edit Note",
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.saveNote() }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Note ID: ${noteId ?: "New"}")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = noteDesc,
                onValueChange = viewModel::onDescChange,
                label = { Text("Description") }
            )
            // Implementation details can be added later
        }
    }
}
