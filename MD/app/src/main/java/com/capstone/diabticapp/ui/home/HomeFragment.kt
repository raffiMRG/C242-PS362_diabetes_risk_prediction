package com.capstone.diabticapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentHomeBinding
import com.capstone.diabticapp.ui.calculate.CalculateActivity
import com.capstone.diabticapp.ui.news.NewsActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        binding.rvHomeCards.isNestedScrollingEnabled = false
        binding.rvHomeCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeCards.adapter = adapter
    }

    private fun navigateToDiabetsCalculator() {
        val intent = Intent(this.context, CalculateActivity::class.java)
        this.context?.startActivity(intent)
    }

    private fun navigateToMedicalHistory() {

    }

    private fun navigateToRelatedNews(){
        val intent = Intent(this.context, NewsActivity::class.java)
        this.context?.startActivity(intent)
    }
}
