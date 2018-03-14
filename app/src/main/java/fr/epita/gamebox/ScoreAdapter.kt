package fr.epita.gamebox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ScoreAdapter(val context : Context, val data : List<Score>) : BaseAdapter() {

    class viewHolder(val itemScore : View) {
        val img_game = itemScore.findViewById<ImageView>(R.id.sc_img_game)
        var t_game = itemScore.findViewById<TextView>(R.id.sc_t_game)
        var t_playername = itemScore.findViewById<TextView>(R.id.sc_t_player)
        var t_res = itemScore.findViewById<TextView>(R.id.sc_t_score)
        var t_date = itemScore.findViewById<TextView>(R.id.sc_t_date)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = LayoutInflater.from(context)

        val rowView : View
        val viewHolder : viewHolder

        if (convertView == null) {
            rowView = inflater.inflate(R.layout.itemscoresview, parent, false)
            viewHolder = viewHolder(rowView)
            rowView.tag = viewHolder
        }
        else {
            rowView = convertView
            viewHolder = convertView.tag as viewHolder
        }

        rowView.setBackgroundColor(getItem(position).background)
        viewHolder.img_game.setImageResource(getItem(position).game_id)
        viewHolder.t_game.text = getItem(position).game
        viewHolder.t_playername.text = getItem(position).player
        viewHolder.t_res.text = getItem(position).score
        viewHolder.t_date.text = getItem(position).date

        return rowView
    }

    override fun getItem(position: Int): Score {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}