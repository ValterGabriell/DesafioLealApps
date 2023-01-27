package com.valtergabriel.desafiolealapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.dto.MockExercise

class ExerciseMockAdapter(private val exerciseList: List<MockExercise>) :
    RecyclerView.Adapter<ExerciseMockAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mockExercise: MockExercise) {
            itemView.findViewById<TextView>(R.id.txt_title_exercise).text = mockExercise.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recycler_view_all_exercise, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(exerciseList[position])
        holder.itemView.findViewById<CardView>(R.id.card_view_all_exercise).setOnClickListener {
            setOnClick?.invoke(
                exerciseList[position].name,
                exerciseList[position].title,
                exerciseList[position].duration,
                exerciseList[position].type,
                exerciseList[position].obs
            )
        }
    }

    var setOnClick: ((Long, String, String, String, String) -> Unit)? = null

    override fun getItemCount(): Int = exerciseList.size


}