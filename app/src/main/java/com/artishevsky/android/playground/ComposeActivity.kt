package com.artishevsky.android.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artishevsky.android.playground.ui.theme.AndroidPlaygroundTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    color = MaterialTheme.colors.background
                ) {
                    EventCard(event = englishLesson)
                }
            }
        }
    }
}

@Composable
fun Title(title: String) {
    Text(text = "Title: $title")
}

@Composable
fun Owner(owner: String) {
    Text(text = "Owner: $owner")
}

@Composable
fun Call(callLink: String) {
    Text(text = "Link: $callLink")
}

@Composable
fun Guests(collapsed: Boolean, content: @Composable () -> Unit) {
    Box(modifier = Modifier.background(Color.Red).size(if (!collapsed) 130.dp else 300.dp)) {
        content()
    }
}

@Composable
fun Badge(text: String) {
    Text(text = "Badge: $text")
}

@Composable
fun EventCard(event: Event) {
    Column {
        Title(event.title)
        Owner(event.owner)
        Call(event.callLink)

        if (event.guests.isNotEmpty()) {
            Guests(collapsed = event.guests.size > 5) {
                if (event.guests.size > 50) {
                    Badge("50+")
                } else {
                    Badge("${event.guests.size}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidPlaygroundTheme {
        EventCard(dentistAppointment)
    }
}

data class Event(
    val title: String,
    val owner: String,
    val callLink: String,
    val guests: List<Guest>,
    val room: String,
    val isRoomSelected: Boolean
)

data class Guest(
    val name: String,
    val surname: String
)

val dentistAppointment = Event(
    title = "Dentist's appointment",
    owner = "Martin",
    callLink = "https://www.dentist.co.uk",
    guests = emptyList(),
    room = "",
    isRoomSelected = false
)

val childBirthday = Event(
    title = "Child's birthday",
    owner = "Martin",
    callLink = "https://www.disneylandparis.com/",
    guests = (1..100).toList().map { Guest("Mr $it", "X") },
    room = "BALLROOM",
    isRoomSelected = true
)

val englishLesson = Event(
    title = "English lesson",
    owner = "Martin",
    callLink = "https://www.school.com/",
    guests = (1..5).toList().map { Guest("Student $it", "X") },
    room = "R03",
    isRoomSelected = true
)