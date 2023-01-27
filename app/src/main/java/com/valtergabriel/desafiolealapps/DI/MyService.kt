package com.valtergabriel.desafiolealapps.DI


import com.valtergabriel.desafiolealapps.repo.TrainingExerciseRepository
import com.valtergabriel.desafiolealapps.repo.UserRepository
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import com.valtergabriel.desafiolealapps.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { UserRepository() }
    single { TrainingExerciseRepository() }

}


val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { TrainingViewModel(get()) }

}