package com.example.wordle

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord

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
                hideKeyboard(findViewById(R.id.mainView))

                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal results to user
                val guess = findViewById<EditText>(R.id.guess1)
                val result = findViewById<EditText>(R.id.guessResult1)

                guess.text = userGuess
                guess.isVisible = true
                result.setText(res)
                result.isVisible = true

                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 2) {
                hideKeyboard(findViewById(R.id.mainView))

                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal result to user
                val guess = findViewById<EditText>(R.id.guess2)
                val result = findViewById<EditText>(R.id.guessResult2)

                guess.text = userGuess
                guess.isVisible = true
                result.setText(res)
                result.isVisible = true

                // Increment guess number
                buttonTaps++
            } else if (buttonTaps == 3) {
                hideKeyboard(findViewById(R.id.mainView))

                // Check user's guess against answer
                res = checkGuess(userGuess.toString().uppercase())

                // Set guess check text to reveal result
                val guess = findViewById<EditText>(R.id.guess3)
                val result = findViewById<EditText>(R.id.guessResult3)

                guess.text = userGuess
                guess.isVisible = true
                result.setText(res)
                result.isVisible = true

                val final = checkWin(result.toString())
                android.util.Log.i("final", final)

                val finalText = findViewById<TextView>(R.id.scoreText)
                finalText.text = final
                finalText.isVisible = true

                // Reset guess number
                buttonTaps = 1
            }
        }
    }

    private fun checkWin(result: String): String {
        var finalScore = ""

        for (i in 0..3) {
            // If res is not correct display loser text
            finalScore = if (result.toString() == "OOOO") {
                "You Win! High Score: 1000"
            } else if (result.toString() == "XXXX") {
                "Keep practicing! Score: 0"
            } else {
                "You're getting there! Score: 500"
            }
        }

        return finalScore
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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
    private fun checkGuess(guess: String): String {
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