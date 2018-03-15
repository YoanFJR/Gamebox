package fr.epita.gamebox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_tictactoe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class tictactoeActivity : AppCompatActivity() {

    private var playerLogin : String = "N/A"
    private var Xturn = true
    private var solutions = mutableListOf<Int>()
    private var markcount = 0
    private var PlayerBitBoard = 0
    private var GuestBitboard = 0
    private var buttons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        initGame()

        ttt_b_reset.setOnClickListener {
            finish()
            startActivity(intent)
        }

        ttt_b_11.setOnClickListener {
            setBitboard(0x100)
            marks(ttt_b_11)
        }
        ttt_b_12.setOnClickListener {
            setBitboard(0x080)
            marks(ttt_b_12)
        }
        ttt_b_13.setOnClickListener {
            setBitboard(0x040)
            marks(ttt_b_13)
        }
        ttt_b_21.setOnClickListener {
            setBitboard(0x020)
            marks(ttt_b_21)
        }
        ttt_b_22.setOnClickListener {
            setBitboard(0x010)
            marks(ttt_b_22)
        }
        ttt_b_23.setOnClickListener {
            setBitboard(0x008)
            marks(ttt_b_23)
        }
        ttt_b_31.setOnClickListener {
            setBitboard(0x004)
            marks(ttt_b_31)
        }
        ttt_b_32.setOnClickListener {
            setBitboard(0x002)
            marks(ttt_b_32)
        }
        ttt_b_33.setOnClickListener {
            setBitboard(0x001)
            marks(ttt_b_33)
        }
    }
    private fun initGame() {
        playerLogin = intent.getStringExtra("ID_PLAYER")
        ttt_t_playerturn.text = playerLogin + "'s turn !"

        buttons.addAll( listOf(ttt_b_11, ttt_b_12, ttt_b_13, ttt_b_21, ttt_b_22, ttt_b_23,
        ttt_b_31, ttt_b_32, ttt_b_33))
        solutions.addAll(listOf(0x1C0, 0x038, 0x007, 0x124, 0x092, 0x049, 0x111, 0x054))
    }

    private fun checkWin() : Boolean {
        for (i in 0 until solutions.count()) {
            if (PlayerBitBoard and(solutions[i]) == solutions[i]) {
                ttt_t_playerturn.text = playerLogin + " wins !"
                disableButtons()
                SendScore("win")
                return true
            }
            else if (GuestBitboard and(solutions[i]) == solutions[i]) {
                ttt_t_playerturn.text = "Guest wins !"
                disableButtons()
                SendScore("loose")
                return true
            }
        }
        return false
    }
    private fun disableButtons() {
        for (i in 0 until buttons.count())
            buttons[i].isClickable = false
    }
    private fun marks (button : Button) {
        if (Xturn) {
            button.setBackgroundResource(R.drawable.xclipart)
            ttt_t_playerturn.text = "Guest's turn !"
        }
        else {
            button.setBackgroundResource(R.drawable.oclipart)
            ttt_t_playerturn.text = playerLogin + "'s turn !"
        }
        button.isClickable = false
        Xturn = !Xturn
        markcount++

        if (!checkWin() && markcount == 9) {
            ttt_t_playerturn.text = "Draw !"
            SendScore("draw")
        }
    }
    private fun setBitboard(bitboard : Int) {
        if (Xturn)
            PlayerBitBoard = PlayerBitBoard or(bitboard)
        else
            GuestBitboard = GuestBitboard or(bitboard)

    }
    private fun SendScore(score : String) {
        if (playerLogin == "nosend")
            return
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()
        val service: IWebService = retrofit.create(IWebService::class.java)
        val callback = object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                Toast.makeText(this@tictactoeActivity, "Score post failed", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null && responseData) {
                            Toast.makeText(this@tictactoeActivity, "Score successfully sent", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                        Toast.makeText(this@tictactoeActivity, "Score post failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        var parameters = hashMapOf(Pair("game_id","1"),
                Pair("score", score),
                Pair("player", playerLogin))
        service.postScore(parameters).enqueue(callback)
    }
}
