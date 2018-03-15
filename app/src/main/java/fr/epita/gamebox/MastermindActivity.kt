package fr.epita.gamebox

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mastermind.*
import java.util.*

class MastermindActivity : AppCompatActivity() {

    private val listColors = listOf(Colors.BLUE, Colors.GREEN, Colors.PURPLE,
            Colors.RED, Colors.WHITE, Colors.YELLOW)
    private var circles = mutableListOf<ImageView>()
    private var solutions = mutableListOf<Colors>()
    private var propostition = mutableListOf<Int>()
    private var position = 0
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
                if (tryNumber > 12) {
                    mm_t_solution.setTextColor(Color.rgb (255, 0, 0))
                    mm_t_solution.text = "You lose.."
                }
                else
                    mm_t_solution.text = "YOU WIN !"

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

        mm_img_p1.setOnLongClickListener {
            mm_cl_backgrnd_selectcolor.visibility = View.VISIBLE
            position = 0
            true
        }
        mm_img_p2.setOnLongClickListener {
            mm_cl_backgrnd_selectcolor.visibility = View.VISIBLE
            position = 1
            true
        }
        mm_img_p3.setOnLongClickListener {
            mm_cl_backgrnd_selectcolor.visibility = View.VISIBLE
            position = 2
            true
        }
        mm_img_p4.setOnLongClickListener {
            mm_cl_backgrnd_selectcolor.visibility = View.VISIBLE
            position = 3
            true
        }

        mm_cl_backgrnd_selectcolor.setOnClickListener {
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_blue.setOnClickListener {
            propostition[position] = 0
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_green.setOnClickListener {
            propostition[position] = 1
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_purple.setOnClickListener {
            propostition[position] = 2
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_red.setOnClickListener {
            propostition[position] = 3
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_white.setOnClickListener {
            propostition[position] = 4
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }
        mm_img_sel_yellow.setOnClickListener {
            propostition[position] = 5
            circles[position].setImageResource(listColors[propostition[position]].ressource)
            mm_cl_backgrnd_selectcolor.visibility = View.INVISIBLE
        }

        mm_ll_propositions.adapter = TryAdapter(this@MastermindActivity, mutableListOf<MastermindTry>())
    }

    private fun initGame() {
        var colors = mutableListOf(Colors.BLUE, Colors.GREEN, Colors.PURPLE,
                Colors.RED, Colors.WHITE, Colors.YELLOW)
        var r = Random()

        circles.addAll(listOf(mm_img_p1, mm_img_p2, mm_img_p3, mm_img_p4))
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
        var mtry = MastermindTry()
        mtry.turnNumber = tryNumber
        mtry.c1 = listColors[propostition[0]].ressource
        mtry.c2 = listColors[propostition[1]].ressource
        mtry.c3 = listColors[propostition[2]].ressource
        mtry.c4 = listColors[propostition[3]].ressource
        mtry.okCount = okcount
        mtry.apCount = apcount

        ((mm_ll_propositions.adapter) as TryAdapter).addTry(mtry)
        mm_ll_propositions.post(Runnable { mm_ll_propositions.setSelection(mm_ll_propositions.getCount() - 1) })
        tryNumber++

        return okcount == 4
    }
}
