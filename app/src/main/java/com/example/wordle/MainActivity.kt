package com.example.wordle

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.set
import androidx.core.view.isVisible
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord
import kotlin.concurrent.timer

@Suppress("CascadeIf")
class MainActivity : AppCompatActivity() {
    private val wordToGuess = getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonTaps = 1
        val guessButton = findViewById<Button>(R.id.guessButton)
        val restartButton = findViewById<Button>(R.id.restartButton)
        val finalText = findViewById<TextView>(R.id.scoreText)
        var res: String

        guessButton.setOnClickListener {
            if (buttonTaps == 1) {
                hideKeyboard(findViewById(R.id.mainView))

                val userGuess = findViewById<EditText>(R.id.userGuess).text
                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 1)
                findViewById<EditText>(R.id.userGuess).setText("")
                buttonTaps++
            } else if (buttonTaps == 2) {
                hideKeyboard(findViewById(R.id.mainView))

                val userGuess = findViewById<EditText>(R.id.userGuess).text
                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 2)
                findViewById<EditText>(R.id.userGuess).setText("")
                buttonTaps++
            } else if (buttonTaps == 3) {
                hideKeyboard(findViewById(R.id.mainView))

                val userGuess = findViewById<EditText>(R.id.userGuess).text
                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 3)
                findViewById<EditText>(R.id.userGuess).setText("")
                guessButton.isEnabled = false

                val final = checkWin(res)
                finalText.text = final
                finalText.isVisible = true

                restartButton.isVisible = true
            }
        }

        restartButton.setOnClickListener {
            // Reset all text and scores
            restartButton.isVisible = false
            buttonTaps = 1
            findViewById<EditText>(R.id.guess1).isVisible = false
            findViewById<EditText>(R.id.guess2).isVisible = false
            findViewById<EditText>(R.id.guess3).isVisible = false
            findViewById<EditText>(R.id.guessResult1).isVisible = false
            findViewById<EditText>(R.id.guessResult2).isVisible = false
            findViewById<EditText>(R.id.guessResult3).isVisible = false
            finalText.isVisible = false
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   userGuess : Editable - what the user entered as their guess
     *   res: String - the resulting 4-character combination of
     *      'O', '+', and/or 'X'
     *   num: Int - the number guess that the player is on
     */
    private fun formatGuess(userGuess: Editable, res: String, num: Int) {
        val g = "guess${num}"
        val r = "guessResult${num}"
        val guess = findViewById<EditText>(resources.getIdentifier(g, "id", packageName))
        val result = findViewById<EditText>(resources.getIdentifier(r, "id", packageName))

        guess.text = userGuess
        guess.isVisible = true
        result.setText(res)
        result.isVisible = true
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
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
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

    /**
     * Parameters / Fields:
     *   result : String - contains 4 character string where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     *
     * Returns a String of:
     *  "You Win! High Score: 1000",
     *  "Keep practicing! Score: 0",
     *  or "You're getting there! Score: 500"
     */
    private fun checkWin(result: String): String {
        var finalScore = ""

        for (i in 0..3) {
            // If res is not correct display loser text
            finalScore = if (result == "OOOO") {
                "${wordToGuess}\nYou Win! High Score: 1000"
            } else if (result == "XXXX") {
                "${wordToGuess}\nKeep practicing! Score: 0"
            } else {
                "${wordToGuess}\nYou're getting there! Score: 500"
            }
        }

        return finalScore
    }

    /**
     * Helper function to hide soft keyboard
     */
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
