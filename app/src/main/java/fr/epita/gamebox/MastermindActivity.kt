package fr.epita.gamebox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mastermind.*
import java.util.*
import android.widget.ScrollView

class MastermindActivity : AppCompatActivity() {

    private val listColors = listOf(Colors.BLUE, Colors.GREEN, Colors.PURPLE,
            Colors.RED, Colors.WHITE, Colors.YELLOW)
    private var solutions = mutableListOf<Colors>()
    private var propostition = mutableListOf<Int>()
    private var tryNumber : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mastermind)
        initGame()

        mm_b_newgame.setOnClickListener {
            finish()
            startActivity(intent)
        }
        mm_b_submit.setOnClickListener {
            if (checktry() || tryNumber > 12) {
                if (tryNumber > 12)
                    Toast.makeText(this@MastermindActivity, "You lose !", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@MastermindActivity, "YOU WIN !", Toast.LENGTH_SHORT).show()

                mm_b_submit.isClickable = false
                mm_t_hiddenSolution.visibility = View.INVISIBLE
            }
        }

        mm_img_p1.setOnClickListener {
            propostition[0] = (propostition[0] + 1) % 6
            mm_img_p1.setImageResource(listColors[propostition[0]].ressource)
        }
        mm_img_p2.setOnClickListener {
            propostition[1] = (propostition[1] + 1) % 6
            mm_img_p2.setImageResource(listColors[propostition[1]].ressource)
        }
        mm_img_p3.setOnClickListener {
            propostition[2] = (propostition[2] + 1) % 6
            mm_img_p3.setImageResource(listColors[propostition[2]].ressource)
        }
        mm_img_p4.setOnClickListener {
            propostition[3] = (propostition[3] + 1) % 6
            mm_img_p4.setImageResource(listColors[propostition[3]].ressource)
        }
    }

    private fun initGame() {
        var colors = mutableListOf(Colors.BLUE, Colors.GREEN, Colors.PURPLE,
                Colors.RED, Colors.WHITE, Colors.YELLOW)
        var r = Random()

        propostition.addAll(listOf(4, 4, 4, 4))
        solutions.add(colors.removeAt(r.nextInt(colors.size - 1)))
        solutions.add(colors.removeAt(r.nextInt(colors.size - 1)))
        solutions.add(colors.removeAt(r.nextInt(colors.size - 1)))
        solutions.add(colors.removeAt(r.nextInt(colors.size - 1)))

        mm_img_s1.setImageResource(solutions[0].ressource)
        mm_img_s2.setImageResource(solutions[1].ressource)
        mm_img_s3.setImageResource(solutions[2].ressource)
        mm_img_s4.setImageResource(solutions[3].ressource)
    }

    private fun checktry() : Boolean {

        var okcount = 0
        var apcount = 0

        // Check inputs
        for (i in 0..2)
            for (j in i+1..3)
                if (listColors[propostition[i]] == listColors[propostition[j]]) {
                    Toast.makeText(this@MastermindActivity, "The try must contain different colors", Toast.LENGTH_SHORT).show()
                    return false
                }

        // Check solution
        for (i in 0..3) {
            if (listColors[propostition[i]] == solutions[i])
                okcount++
            else
                (0..3).filter { listColors[propostition[i]] == solutions[it] }
                      .forEach { apcount++ }
        }

        // Add a proposition
        var t_try = fr.epita.gamebox.itemtry(this@MastermindActivity, tryNumber.toString() + ".",
                listColors[propostition[0]].ressource,
                listColors[propostition[1]].ressource,
                listColors[propostition[2]].ressource,
                listColors[propostition[3]].ressource,
                okcount.toString(), apcount.toString())
        tryNumber++
        mm_ll_propositions.addView(t_try)
        mm_sc_propositions.post({ mm_sc_propositions.fullScroll(ScrollView.FOCUS_DOWN) })

        return okcount == 4
    }
}
