package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityFeedBinding
import com.valtergabriel.desafiolealapps.mock.MockTrain
import com.valtergabriel.desafiolealapps.ui.adapter.FeedAdapter
import com.valtergabriel.desafiolealapps.util.Constants.EXEMPLE
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
        val btnAddTraining = binding.btnAddTraining
        val txtMyTraining = binding.txtMyTraining

        setUserTrainingListOnRecyclerView()



        btnAddTraining.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                /**
                 * Se passa esse parametro estatico para o outro lado, com a mesma chave, para que se possa
                 * inserir um valor vazio no edit text e também inserir o valor setado pelo usuário quando um
                 * exercicio é adicionado ao treino. Caso contrario, o valor do edit text teria que ser deletado
                 * sempre que fosse escrever nele.
                 */
                it.putExtra("traning_name_from_last_exercise_added", EXEMPLE)
                it.putExtra("wanna_edit", false)
                startActivity(it)
            }
        }

        txtMyTraining.setOnClickListener {
            Intent(this, CreateTrainingActivity::class.java).also {
                it.putExtra("traning_name_from_last_exercise_added", "")
                it.putExtra("wanna_edit", false)
                startActivity(it)
            }
        }

    }

    private fun setUserTrainingListOnRecyclerView(){
        val feedRecyclerView = binding.feedRecyclerView

        trainingViewModel.getTraningsFromFirebase()
        trainingViewModel.listTraining.observe(this){ trainings ->
            if (trainings.isNotEmpty()){
                binding.loadingList.visibility = View.GONE
                binding.feedRecyclerView.visibility = View.VISIBLE

                adapter = FeedAdapter(trainings)
                feedRecyclerView.adapter = adapter
                feedRecyclerView.layoutManager = LinearLayoutManager(this)
                adapter.setOnClick = { staticTitle, title, desc ->
                    Intent(this, VizualizeTraining::class.java).also { intent ->
                        intent.putExtra("traning_name_from_feed", title)
                        intent.putExtra("traning_desc_from_feed", desc)
                        intent.putExtra("traning_static_name_from_feed", staticTitle)
                        startActivity(intent)
                    }
                }
            }else{
                binding.loadingList.visibility = View.GONE
                binding.emptyList.visibility = View.VISIBLE
            }



        }
    }
}