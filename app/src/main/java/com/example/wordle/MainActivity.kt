package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonTaps = 1
        val guessButton = findViewById<Button>(R.id.guessButton)

        guessButton.setOnClickListener {
            // Check for number of guesses
            if (buttonTaps == 1) {
                // Pass in user string
                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 2) {
                // Pass in user string
                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 3) {
                // Pass in user string to checkGuess()
                // Reset guess number
                buttonTaps = 1
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


}