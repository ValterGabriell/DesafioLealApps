package com.valtergabriel.desafiolealapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.mock.MockTrain
import com.valtergabriel.desafiolealapps.mock.Training

class FeedAdapter(private val trainList: ArrayList<Training>) :
    RecyclerView.Adapter<FeedAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(training: Training) {
            itemView.findViewById<TextView>(R.id.txt_title_exercise).text = training.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(trainList[position])
        holder.itemView.findViewById<CardView>(R.id.card_view_feed).setOnClickListener {
            setOnClick?.invoke(position, trainList[position].name, trainList[position].dateTime)
        }
    }

    var setOnClick : ((Int, String, String) -> Unit)? = null

    override fun getItemCount(): Int = trainList.size


}