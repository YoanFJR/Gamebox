package fr.epita.gamebox

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
