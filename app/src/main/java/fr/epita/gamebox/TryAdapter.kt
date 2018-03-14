package fr.epita.gamebox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class TryAdapter (val context : Context, val data : MutableList<MastermindTry>) : BaseAdapter() {
    class viewHolder(val itemTry : View) {
        val turnNumber = itemTry.findViewById<TextView>(R.id.mm_t_trynumber)
        var c1 = itemTry.findViewById<ImageView>(R.id.mm_img_t1)
        var c2 = itemTry.findViewById<ImageView>(R.id.mm_img_t2)
        var c3 = itemTry.findViewById<ImageView>(R.id.mm_img_t3)
        var c4 = itemTry.findViewById<ImageView>(R.id.mm_img_t4)
        var okCount = itemTry.findViewById<TextView>(R.id.mm_t_ok)
        var apCount = itemTry.findViewById<TextView>(R.id.mm_t_ap)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = LayoutInflater.from(context)

        val rowView : View
        val viewHolder : viewHolder

        if (convertView == null) {
            rowView = inflater.inflate(R.layout.itemtry, parent, false)
            viewHolder = viewHolder(rowView)
            rowView.tag = viewHolder
        }
        else {
            rowView = convertView
            viewHolder = convertView.tag as viewHolder
        }

        viewHolder.turnNumber.text = getItem(position).turnNumber.toString() + "."
        viewHolder.c1.setImageResource(getItem(position).c1)
        viewHolder.c2.setImageResource(getItem(position).c2)
        viewHolder.c3.setImageResource(getItem(position).c3)
        viewHolder.c4.setImageResource(getItem(position).c4)
        viewHolder.okCount.text = getItem(position).okCount.toString()
        viewHolder.apCount.text = getItem(position).apCount.toString()

        return rowView
    }

    override fun getItem(position: Int): MastermindTry {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    public fun addTry(mastermindTry : MastermindTry) {
        data.add(mastermindTry)
        notifyDataSetChanged()
    }
}