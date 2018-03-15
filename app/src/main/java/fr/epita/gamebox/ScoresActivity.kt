package fr.epita.gamebox

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : AppCompatActivity() {

    var list : MutableList<Score> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        sc_t_playername.text = intent.getStringExtra("ID_PLAYER")

        displayScores(false)
        sc_b_filter.setOnClickListener {
            unfocus(this@ScoresActivity)
        }
        sc_t_mainLayout.setOnClickListener {
            unfocus(this@ScoresActivity)
        }
        sc_b_filter.setOnClickListener {
            sc_sc_loading.visibility = View.VISIBLE
            if (sc_s_input_filter.text.toString().isEmpty())
                displayScores(false)
            else
                displayScores(true)
        }
    }

    fun unfocus(mActivity: Activity) {
        sc_t_mainLayout.requestFocus()
        // Check if no view has focus:
        val view = mActivity.currentFocus
        if (view != null) {
            val inputManager = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun displayScores(filter: Boolean) {
        val data = arrayListOf<Score>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: IWebService = retrofit.create(IWebService::class.java)

        val callback = object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>?, t: Throwable?) {
                Toast.makeText(this@ScoresActivity, "An error has occurred during connection", Toast.LENGTH_SHORT).show()
                sc_sc_loading.visibility = View.INVISIBLE
                Log.d("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<List<Score>>?, response: Response<List<Score>>?) {
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            DisplayList(responseData, filter)
                        }
                        sc_sc_loading.visibility = View.INVISIBLE
                    }
                    else {
                        Toast.makeText(this@ScoresActivity, "An error has occurred in the request", Toast.LENGTH_SHORT).show()
                        sc_sc_loading.visibility = View.INVISIBLE
                    }
                }
                else {
                    Toast.makeText(this@ScoresActivity, "An error has occurred in the request", Toast.LENGTH_SHORT).show()
                    sc_sc_loading.visibility = View.INVISIBLE
                }
            }
        }
        service.scoresList().enqueue(callback)
    }

    private fun DisplayList(data : List<Score>, filter: Boolean) {
        val gameImage = hashMapOf<Int, Int>(Pair(9, R.drawable.slidingpuzzle),
                Pair(3, R.drawable.sudoku),
                Pair(1, R.drawable.tictactoe),
                Pair(6, R.drawable.gameoflife),
                Pair(8, R.drawable.simon),
                Pair(4, R.drawable.battleship),
                Pair(2, R.drawable.hangman),
                Pair(5, R.drawable.minesweeper),
                Pair(7, R.drawable.memory),
                Pair(10, R.drawable.mastermind))

        data.forEach{
            it.date = it.date.substring(0, 10)
            it.game_id = gameImage[it.game_id] as Int
            when {
                it.score == "win" -> it.background = 0x44DAF7A6
                it.score == "loose" -> it.background = 0x44FFB9B9
                else -> it.background = 0x44FFEEB9
            }
        }
        if (filter)
            sc_sc_score.adapter = ScoreAdapter(this@ScoresActivity, data.filter({score ->
                score.player.toLowerCase() == sc_s_input_filter.text.toString().toLowerCase() ||
                score.score.toLowerCase() == sc_s_input_filter.text.toString().toLowerCase() ||
                score.game.toLowerCase() == sc_s_input_filter.text.toString().toLowerCase()}))
        else
            sc_sc_score.adapter = ScoreAdapter(this@ScoresActivity, data)
    }
}
