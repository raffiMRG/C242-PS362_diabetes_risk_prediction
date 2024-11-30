package com.capstone.diabticapp.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentHomeBinding
import com.capstone.diabticapp.ui.calculate.CalculateActivity
import com.capstone.diabticapp.ui.history.HistoryActivity
import com.capstone.diabticapp.ui.news.NewsActivity
import com.capstone.diabticapp.ui.setting.SettingsViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isDiabetets: Boolean = false

    private val viewModel: HomeViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeCards = listOf(
            HomeCardItem(R.drawable.ic_stomatch, "Diabets Calculator"),
            HomeCardItem(R.drawable.ic_bloodrop, "Medical History"),
            HomeCardItem(R.drawable.ic_news, "Related News"),
        )

        val adapter = HomeAdapter(homeCards) { cardItem ->
            when (cardItem.title) {
                "Diabets Calculator" -> navigateToDiabetsCalculator()
                "Medical History" -> navigateToMedicalHistory()
                "Related News" -> navigateToRelatedNews()
            }
        }

        if (isDiabetets){
            binding.profileBackgroud.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_image_green)
            binding.diabetsStatus.text = getString(R.string.status_diabets_negative)
        }else{
            binding.profileBackgroud.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_image_red)
            binding.diabetsStatus.text = getString(R.string.status_diabets_positive)
        }


        binding.rvHomeCards.isNestedScrollingEnabled = false
        binding.rvHomeCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeCards.adapter = adapter

        setBackgroundBasedOnMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.username.observe(viewLifecycleOwner) { name ->
            binding.tvGreeting.text = name
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setBackgroundBasedOnMode()
    }

    private fun navigateToDiabetsCalculator() {
        val intent = Intent(this.context, CalculateActivity::class.java)
        this.context?.startActivity(intent)
    }

    private fun navigateToMedicalHistory() {
        val intent = Intent(this.context, HistoryActivity::class.java)
        this.context?.startActivity(intent)
    }

    private fun navigateToRelatedNews(){
        val intent = Intent(this.context, NewsActivity::class.java)
        this.context?.startActivity(intent)
    }

    private fun setBackgroundBasedOnMode() {
        // Ganti dengan ID root layout Anda
        if (isNightMode()) {
            binding.heroLayout.setBackgroundResource(R.drawable.hero1_3night) // Warna untuk Night Mode
            binding.tvGreeting.setBackgroundResource(R.color.night_primary)
        } else {
            binding.heroLayout.setBackgroundResource(R.drawable.hero1_2) // Warna untuk Light Mode
            binding.tvGreeting.setBackgroundResource(R.color.blue_primary)
        }
    }

    private fun isNightMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}