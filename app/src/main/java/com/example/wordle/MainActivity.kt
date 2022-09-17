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
import androidx.core.view.isVisible
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord

@Suppress("CascadeIf")
class MainActivity : AppCompatActivity() {
    private val wordToGuess = getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonTaps = 1
        val userGuess = findViewById<EditText>(R.id.userGuess).text
        val guessButton = findViewById<Button>(R.id.guessButton)
        var res: String

        guessButton.setOnClickListener {
            if (buttonTaps == 1) {
                hideKeyboard(findViewById(R.id.mainView))

                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 1)
                buttonTaps++
            } else if (buttonTaps == 2) {
                hideKeyboard(findViewById(R.id.mainView))

                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 2)
                buttonTaps++
            } else if (buttonTaps == 3) {
                hideKeyboard(findViewById(R.id.mainView))

                res = checkGuess(userGuess.toString().uppercase())
                formatGuess(userGuess, res, 3)

                val final = checkWin(res)
                val finalText = findViewById<TextView>(R.id.scoreText)
                finalText.text = final
                finalText.isVisible = true
                buttonTaps = 1
            }
        }
    }

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

    private fun checkWin(result: String): String {
        var finalScore = ""

        for (i in 0..3) {
            // If res is not correct display loser text
            finalScore = if (result == "OOOO") {
                "You Win! High Score: 1000"
            } else if (result == "XXXX") {
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