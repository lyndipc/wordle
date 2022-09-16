package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonTaps = 1
        var userGuess = findViewById<EditText>(R.id.userGuess).text
        val guessButton = findViewById<Button>(R.id.guessButton)
        var res = ""

        guessButton.setOnClickListener {
            // Check for number of guesses
            if (buttonTaps == 1) {

                android.util.Log.i("User input 1:", userGuess.toString().uppercase())
                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal results to user
                android.util.Log.i("First guess", res)
                findViewById<EditText>(R.id.guessResult1).setText(res)

                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 2) {

                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal result to user
                android.util.Log.i("Second guess", res)
                findViewById<EditText>(R.id.guessResult2).setText(res)

                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 3) {

                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal result
                android.util.Log.i("Third guess", res)
                findViewById<EditText>(R.id.guessResult3).setText(res)

                // If res is not correct display loser text


                // else display winner text

                // Reset guess number
                buttonTaps = 1
            }
        }

//        userGuess.setOnClickListener {
//            // Detect how many letters user has typed in
//
//            // Disable Guess button if user has too few or too many letters
//
//        }
    }

    private val wordToGuess = getRandomFourLetterWord()

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
        android.util.Log.i("Word", wordToGuess)
        var result = ""
        for (i in 0..3) {
            android.util.Log.i("guess[i] = ", guess[i].toString())
            android.util.Log.i("wordToGuess[i]", wordToGuess[i].toString())
            result += if (guess[i] == wordToGuess[i]) {
                "O"
            } else if (guess[i] in wordToGuess) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }
}