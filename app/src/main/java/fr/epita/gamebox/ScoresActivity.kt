package fr.epita.gamebox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Script
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_scores.*
import java.util.HashMap
import java.util.function.Consumer

class ScoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        sc_t_playername.text = intent.getStringExtra("ID_PLAYER")
        displayScores()
    }

    private fun displayScores() {
        val data = arrayListOf<Score>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: IWebService = retrofit.create(IWebService::class.java)

        val callback = object : Callback<List<Score>> {
            override fun onFailure(call: Call<List<Score>>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails
                val tverror = TextView(this@ScoresActivity)
                tverror.text = "Error loading"
                sc_sc_score.addView(tverror)
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
                            data.addAll(responseData)
                            data.forEach{
                                it.date = it.date.substring(0, 10)
                                it.game_id = gameImage[it.game_id] as Int
                                when {
                                    it.score == "win" -> it.background = 0x44DAF7A6
                                    it.score == "loose" -> it.background = 0x44FFB9B9
                                    else -> it.background = 0x44FFEEB9
                                }
                            }
                            sc_sc_score.adapter = ScoreAdapter(this@ScoresActivity, data)

                        }
                        sc_sc_loading.visibility = View.INVISIBLE
                    }
                }
            }
        }
        service.scoresList().enqueue(callback)
    }
}
