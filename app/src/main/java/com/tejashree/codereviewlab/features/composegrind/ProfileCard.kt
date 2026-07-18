package com.tejashree.codereviewlab.features.composegrind

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Profile Card
//
//Circular avatar
//Name, role, location
//Follow button
//Practice: Row, Column, Image, Card, spacing
@Preview(showBackground = true)
@Composable
fun PreviewProfileCard() {
    ProfileCard(User("Tejashree", "Android Developer", "San Francisco"))
}

data class User(
    val name: String,
    val role: String,
    val location: String
)

@Composable
fun ProfileCard(user: User) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(user.name.first().toString())
                }
                Column {
                    Text(user.name, style = MaterialTheme.typography.titleMedium)
                    Text(user.role, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Text(user.location)
                }
                TextButton(onClick = { }) {
                    Text("Follow")
                }
            }
        }
    }

}