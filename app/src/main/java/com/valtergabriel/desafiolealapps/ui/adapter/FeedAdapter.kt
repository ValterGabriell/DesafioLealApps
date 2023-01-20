package com.valtergabriel.desafiolealapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.mock.MockTrain

class FeedAdapter(private val trainList: List<MockTrain>) :
    RecyclerView.Adapter<FeedAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mockTrain: MockTrain) {
            itemView.findViewById<TextView>(R.id.txt_title_recycler_view).text = mockTrain.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(trainList[position])
    }

    var setOnClick : ((Int, Int) -> Unit)? = null

    override fun getItemCount(): Int = trainList.size

}