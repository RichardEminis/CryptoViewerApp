package com.example.cryptoviewerapp.ui.cryptoDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cryptoviewerapp.R
import com.example.cryptoviewerapp.databinding.FragmentCryptoDetailsBinding

class CryptoDetailsFragment : Fragment() {

    private val binding: FragmentCryptoDetailsBinding by lazy {
        FragmentCryptoDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel: CryptoDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}