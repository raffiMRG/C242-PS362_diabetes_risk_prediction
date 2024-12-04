package com.capstone.diabticapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.diabticapp.data.CalculateRepository
import com.capstone.diabticapp.data.HistoryRepository
import com.capstone.diabticapp.di.Injection
import com.capstone.diabticapp.ui.calculate.CalculateViewModel
import com.capstone.diabticapp.ui.history.HistoryViewModel

class HistoryViewModelFactory(private val repository: HistoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HistoryViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): HistoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HistoryViewModelFactory::class.java) {
//                    val userRepository = Injection.provideRepository(context)
                    val storyRepository = Injection.provideHistoryRepository(context)
//                    INSTANCE = ViewModelFactory(userRepository, storyRepository)
                    INSTANCE = HistoryViewModelFactory(storyRepository)
                }
            }
            return INSTANCE as HistoryViewModelFactory
        }
    }
}