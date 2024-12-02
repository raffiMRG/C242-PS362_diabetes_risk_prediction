package com.capstone.diabticapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.diabticapp.data.CalculateRepository
import com.capstone.diabticapp.di.Injection
import com.capstone.diabticapp.ui.calculate.CalculateViewModel

class CalculateViewModelFactory(private val repository: CalculateRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CalculateViewModel::class.java) -> {
                CalculateViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CalculateViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): CalculateViewModelFactory {
            if (INSTANCE == null) {
                synchronized(CalculateViewModelFactory::class.java) {
                    val storyRepository = Injection.provideCalculateRepository(context)
                    INSTANCE = CalculateViewModelFactory(storyRepository)
                }
            }
            return INSTANCE as CalculateViewModelFactory
        }
    }
}