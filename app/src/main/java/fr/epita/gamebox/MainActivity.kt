package fr.epita.gamebox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t_mainLayout.setOnClickListener {
            unfocus(this@MainActivity)
        }

        t_b_tictactoe.setOnClickListener {
            val intent = Intent(this@MainActivity, tictactoeActivity::class.java)
            intent.putExtra("ID_PLAYER", t_in_player.text.toString())
            startActivity(intent)
        }
        t_b_mastermind.setOnClickListener {
            val intent = Intent(this@MainActivity, MastermindActivity::class.java)
            intent.putExtra("ID_PLAYER", t_in_player.text.toString())
            startActivity(intent)
        }
        t_b_scores.setOnClickListener {
            val intent = Intent(this@MainActivity, ScoresActivity::class.java)
            intent.putExtra("ID_PLAYER", t_in_player.text.toString())
            startActivity(intent)
        }
    }

    fun unfocus(mActivity: Activity) {
        t_mainLayout.requestFocus()
        // Check if no view has focus:
        val view = mActivity.currentFocus
        if (view != null) {
            val inputManager = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
