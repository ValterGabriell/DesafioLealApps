package com.valtergabriel.desafiolealapps.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.dto.Exercises

class ExerciseFirebaseAdapter(private val exerciseList: List<Exercises>) :
    RecyclerView.Adapter<ExerciseFirebaseAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(exercise: Exercises) {
            itemView.findViewById<TextView>(R.id.txt_title_).text = exercise.title
            itemView.findViewById<TextView>(R.id.txtMinutes).text = exercise.duration + " minutos"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view_exercise, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(exerciseList[position])
        holder.itemView.findViewById<CardView>(R.id.card_view_exercise).setOnClickListener {
            setOnClick?.invoke(exerciseList[position].title, exerciseList[position].duration, exerciseList[position].type, exerciseList[position].obs, exerciseList[position].name)
        }
    }

    var setOnClick : ((String, String, String, String, Long) -> Unit)? = null

    override fun getItemCount(): Int = exerciseList.size


}