package com.tejashree.codereviewlab.features.vehicle.parkandgo.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.toParkAndGoTimestamp(): String {
    val formatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    return formatter.format(this.time)
}
