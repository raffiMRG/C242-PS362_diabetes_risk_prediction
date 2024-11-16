package com.capstone.diabticapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentHomeBinding

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
            HomeCardItem(R.drawable.ic_bloodrop, "Medical History")
        )

        val adapter = HomeAdapter(homeCards) { cardItem ->
            when (cardItem.title) {
                "Your Diet Chart" -> navigateToDiabetsCalculator()
                "Medical History" -> navigateToMedicalHistory()
            }
        }

        binding.rvHomeCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeCards.adapter = adapter
    }

    private fun navigateToDiabetsCalculator() {

    }

    private fun navigateToMedicalHistory() {

    }
}
