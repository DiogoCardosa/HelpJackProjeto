package pt.ipg.helpjackprojeto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackjackHelperApp()
        }
    }
}

@Composable
fun BlackjackHelperApp() {
    var card1 by remember { mutableStateOf("") }
    var card2 by remember { mutableStateOf("") }
    var dealerCard by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Blackjack Helper", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = card1,
            onValueChange = { card1 = it },
            label = { Text("Enter first card value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = card2,
            onValueChange = { card2 = it },
            label = { Text("Enter second card value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = dealerCard,
            onValueChange = { dealerCard = it },
            label = { Text("Enter dealer's card value") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            result = getBlackjackAdvice(card1, card2, dealerCard)
        }) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = result, fontSize = 20.sp)
    }
}

fun getBlackjackAdvice(card1: String, card2: String, dealerCard: String): String {
    val card1Value = card1.toIntOrNull() ?: return "Invalid first card value"
    val card2Value = card2.toIntOrNull() ?: return "Invalid second card value"
    val dealerCardValue = dealerCard.toIntOrNull() ?: return "Invalid dealer's card value"

    val playerTotal = card1Value + card2Value

    return when {
        playerTotal >= 17 -> "Stand"
        playerTotal >= 13 && dealerCardValue in 2..6 -> "Stand"
        playerTotal == 12 && dealerCardValue in 4..6 -> "Stand"
        playerTotal == 11 -> "Double"
        playerTotal == 10 && dealerCardValue !in 10..11 -> "Double"
        playerTotal == 9 && dealerCardValue in 3..6 -> "Double"
        playerTotal in 5..8 -> "Hit"
        card1Value == card2Value -> when (card1Value) {
            8, 11 -> "Split"
            2, 3, 7 -> if (dealerCardValue in 2..7) "Split" else "Hit"
            6 -> if (dealerCardValue in 2..6) "Split" else "Hit"
            4 -> if (dealerCardValue in 5..6) "Split" else "Hit"
            9 -> if (dealerCardValue in 2..6 || dealerCardValue in 8..9) "Split" else "Stand"
            10 -> "Stand"
            else -> "Hit"
        }
        else -> "Hit"
    }
}
