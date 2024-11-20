package com.capstone.diabticapp.ui.calculate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityCalculateBinding
import com.capstone.diabticapp.databinding.LayoutDiabetesDetectedBinding
import com.capstone.diabticapp.databinding.LayoutNoDiabetesBinding

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        myAppBar.setOnBackPressedListener{
//            onBackPressed()
//        }
    }

    // TODO: Gunakan fungsi ini untuk menampilkan layout user yang terkena Diabetes
    private fun showDiabetesDetectedLayout() {
        val binding = LayoutDiabetesDetectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextDiabetesDetected.setOnClickListener {
            // TODO: bisa navigasi ke halaman utama atau menyimpan Hasil Prediksi ?
        }
    }

    // TODO: Gunakan fungsi ini untuk menampilkan layout user yang tidak terkena Diabetes
    private fun showNoDiabetesLayout() {
        val binding = LayoutNoDiabetesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextNoDiabetes.setOnClickListener {
            // TODO: bisa navigasi ke halaman utama atau menyimpan Hasil Prediksi ?
        }
    }

}