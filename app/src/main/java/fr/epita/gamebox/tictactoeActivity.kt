package fr.epita.gamebox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_tictactoe.*

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
                return true
            }
            else if (GuestBitboard and(solutions[i]) == solutions[i]) {
                ttt_t_playerturn.text = "Guest wins !"
                disableButtons()
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
            button.text = "X"
            ttt_t_playerturn.text = "Guest's turn !"
        }
        else {
            button.text = "O"
            ttt_t_playerturn.text = playerLogin + "'s turn !"
        }
        button.isClickable = false
        Xturn = !Xturn
        markcount++

        if (!checkWin() && markcount == 9) {
            ttt_t_playerturn.text = "Draw !"
        }
    }
    private fun setBitboard(bitboard : Int) {
        if (Xturn)
            PlayerBitBoard = PlayerBitBoard or(bitboard)
        else
            GuestBitboard = GuestBitboard or(bitboard)

    }
    /*
    private fun DebugPrint(bb : Int) {
        for (i in 0..2)
            for (j in 0..2) {
                if (bb)
            }
    }*/


}
