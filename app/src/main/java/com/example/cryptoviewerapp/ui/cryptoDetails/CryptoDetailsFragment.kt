package com.example.cryptoviewerapp.ui.cryptoDetails

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptoviewerapp.R
import com.example.cryptoviewerapp.databinding.FragmentCryptoDetailsBinding
import com.example.cryptoviewerapp.model.CryptoCurrency
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailsFragment : Fragment() {

    private val binding: FragmentCryptoDetailsBinding by lazy {
        FragmentCryptoDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel: CryptoDetailsViewModel by viewModels()
    private val args: CryptoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initSwipeToRefresh()

        viewModel.cryptoDetailsUiState.observe(viewLifecycleOwner) { cryptoUiState ->
            cryptoUiState?.let {
                if (it.isLoading) {
                    showLoadingState()
                } else  {
                    showContentState()
                }
            } ?: showErrorState()
        }
    }

    private fun initUI() {
        viewModel.cryptoDetailsUiState.observe(viewLifecycleOwner) { state ->

            binding.tvDetailsToolbarTitle.text = state.detailsCryptocurrency?.name
            binding.tvDetailsDescription.text = state.detailsCryptocurrency?.description?.en ?: ""
            binding.tvDetailsCategory.text = state.detailsCryptocurrency?.categories.toString()

            Glide.with(this)
                .load(state.detailsCryptocurrency?.image?.large ?: R.drawable.btc)
                .placeholder(R.drawable.btc)
                .error(R.drawable.btc)
                .into(binding.ivDetailsCryptoImage)

            binding.btnBack.setOnClickListener {
                findNavController().navigate(CryptoDetailsFragmentDirections.actionCryptoDetailsFragmentToCryptoListFragment())
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isInternetAvailable()) {
                viewModel.getCryptoCurrenciesDetails(args.cryptoId)
            } else {
                Snackbar.make(
                    binding.root,
                    "Произошла ошибка при загрузке",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showErrorState() {
        binding.descriptionView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.btnTry.setOnClickListener {
            viewModel.getCryptoCurrenciesDetails(args.cryptoId)
        }
    }

    private fun showLoadingState() {
        binding.errorView.visibility = View.GONE
        binding.descriptionView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showContentState() {
        binding.descriptionView.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}