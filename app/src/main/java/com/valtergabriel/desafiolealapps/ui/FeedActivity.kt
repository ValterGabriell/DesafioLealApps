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

class FeedActivity : AppCompatActivity() {

    private lateinit var adapter: FeedAdapter
    private lateinit var binding: ActivityFeedBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val list = MockTrain().createTraining()
        adapter = FeedAdapter(list)
        binding.feedRecyclerView.adapter = adapter
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAddTraining.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}