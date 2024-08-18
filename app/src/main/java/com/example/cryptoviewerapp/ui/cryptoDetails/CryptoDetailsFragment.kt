package com.example.cryptoviewerapp.ui.cryptoDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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

        initUI()
    }

    private fun initUI() {
        viewModel.cryptoDetailsUiState.observe(viewLifecycleOwner) { state ->

            binding.tvDetailsToolbarTitle.text = state.detailsCryptocurrency?.name
            binding.tvDetailsDescription.text = state.detailsCryptocurrency?.description
            binding.tvDetailsCategory.text = state.detailsCryptocurrency?.categories.toString()

            Glide.with(this)
                .load(state.detailsCryptocurrency ?: R.drawable.btc)
                .placeholder(R.drawable.btc)
                .error(R.drawable.btc)
                .into(binding.ivDetailsCryptoImage)

            binding.btnBack.setOnClickListener {
                findNavController().navigate(CryptoDetailsFragmentDirections.actionCryptoDetailsFragmentToCryptoListFragment())
            }
        }
    }
}