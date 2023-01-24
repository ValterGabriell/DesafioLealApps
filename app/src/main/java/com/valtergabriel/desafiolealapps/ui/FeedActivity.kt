package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityFeedBinding
import com.valtergabriel.desafiolealapps.mock.MockTrain
import com.valtergabriel.desafiolealapps.ui.adapter.FeedAdapter
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject

class FeedActivity : AppCompatActivity() {

    private lateinit var adapter: FeedAdapter
    private lateinit var binding: ActivityFeedBinding
    private val trainingViewModel by inject<TrainingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trainingViewModel.getTraningsFromFirebase()
        trainingViewModel.listTraining.observe(this){ trainings ->
            adapter = FeedAdapter(trainings)
            binding.feedRecyclerView.adapter = adapter
            binding.feedRecyclerView.layoutManager = LinearLayoutManager(this)

            adapter.setOnClick = { pos, trainingName, id ->
                Intent(this, CreateTrainingActivity::class.java).also { intent ->
                    intent.putExtra("traning_name_from_feed", trainingName)
                    intent.putExtra("traning_id", id)
                    startActivity(intent)
                }

            }


        }

        binding.btnAddTraining.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.txtMyTraining.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}