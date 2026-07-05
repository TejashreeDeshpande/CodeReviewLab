package com.tejashree.codereviewlab.features.mvvm.notes.data

import androidx.compose.runtime.Immutable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Immutable
data class Note(
    val id: String,
    val desc: String,
    val lastUpdatedTimeStamp: Long
) {
    val title: String
        get() = desc.split(" ")[0]

    val lastUpdatedDate: String
        get() = SimpleDateFormat(
            "MMM dd, yyyy hh:mm a",
            Locale.getDefault()
        ).format(Date(lastUpdatedTimeStamp))
}