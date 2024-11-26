package com.capstone.diabticapp.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentChangePasswordBinding
import com.capstone.diabticapp.databinding.FragmentChangePhoneBinding


class ChangePhoneFragment : Fragment() {
    private var _binding: FragmentChangePhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}