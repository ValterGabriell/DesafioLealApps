package com.valtergabriel.desafiolealapps.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.valtergabriel.desafiolealapps.databinding.ActivityFeedBinding
import com.valtergabriel.desafiolealapps.ui.adapter.FeedAdapter
import com.valtergabriel.desafiolealapps.util.Constants.EXEMPLE
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_DESCRIPTION_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_NAME_FROM_LAST_EXERCISES_ADDED
import com.valtergabriel.desafiolealapps.util.Constants.TRAINING_STATIC_NAME_FROM_FEED_SCREEN
import com.valtergabriel.desafiolealapps.util.Constants.WANNA_EDIT
import com.valtergabriel.desafiolealapps.util.Firebase
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
        binding.btnLogout.setOnClickListener {
            logout()
        }



        btnAddTraining.setOnClickListener {
            Intent(this, CreateAndEditTrainingActivity::class.java).also {
                /**
                 * Passa-se esse parametro estatico para o outro lado, com a mesma chave, para que se possa
                 * inserir um valor vazio no edit text e também inserir o valor setado pelo usuário quando um
                 * exercicio é adicionado ao treino. Caso contrario, o valor do edit text teria que ser deletado
                 * sempre que fosse escrever nele.
                 */
                it.putExtra(TRAINING_NAME_FROM_LAST_EXERCISES_ADDED, EXEMPLE)
                it.putExtra(WANNA_EDIT, false)
                startActivity(it)
            }
        }

        txtMyTraining.setOnClickListener {
            Intent(this, CreateAndEditTrainingActivity::class.java).also {
                it.putExtra("traning_name_from_last_exercise_added", "")
                it.putExtra("wanna_edit", false)
                startActivity(it)
            }
        }

    }

    private fun setUserTrainingListOnRecyclerView() {
        val feedRecyclerView = binding.feedRecyclerView

        trainingViewModel.getTraningsFromFirebase()
        trainingViewModel.listTraining.observe(this) { trainings ->
            if (trainings.isNotEmpty()) {
                binding.loadingList.visibility = View.GONE
                binding.feedRecyclerView.visibility = View.VISIBLE

                adapter = FeedAdapter(trainings)
                feedRecyclerView.adapter = adapter
                feedRecyclerView.layoutManager = LinearLayoutManager(this)
                adapter.setOnClick = { staticTitle, title, desc ->
                    Intent(this, VizualizeTraining::class.java).also { intent ->
                        intent.putExtra(TRAINING_NAME_FROM_FEED_SCREEN, title)
                        intent.putExtra(TRAINING_DESCRIPTION_FROM_FEED_SCREEN, desc)
                        intent.putExtra(TRAINING_STATIC_NAME_FROM_FEED_SCREEN, staticTitle)
                        startActivity(intent)
                    }
                }
            } else {
                binding.loadingList.visibility = View.GONE
                binding.emptyList.visibility = View.VISIBLE
            }


        }
    }

    private fun logout() {
        Firebase.getAuth().signOut().also {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}