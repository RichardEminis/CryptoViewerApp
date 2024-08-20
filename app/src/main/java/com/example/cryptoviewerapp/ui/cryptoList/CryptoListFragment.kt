package com.example.cryptoviewerapp.ui.cryptoList

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
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoviewerapp.R
import com.example.cryptoviewerapp.databinding.FragmentCryptoListBinding
import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.ulils.ERROR_MESSAGE
import com.example.cryptoviewerapp.ulils.RUB_CURRENCY
import com.example.cryptoviewerapp.ulils.USD_CURRENCY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private val viewModel: CryptoViewModel by viewModels()
    private lateinit var adapter: CryptoListAdapter

    private var _binding: FragmentCryptoListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initSwipeToRefresh()
        initChipGroupListener()

        viewModel.cryptoCurrencies.observe(viewLifecycleOwner) { cryptoUiState ->
            cryptoUiState?.let {
                if (it.isLoading) {
                    showLoadingState()
                } else if (!it.cryptocurrency.isNullOrEmpty()) {
                    showContentState(it.cryptocurrency)
                } else {
                    showErrorState()
                }
            } ?: showErrorState()
        }
    }

    private fun initRecycler() {
        adapter = CryptoListAdapter()
        recyclerView = binding.rvCryptoRecycler
        recyclerView?.adapter = adapter

        adapter.setOnItemClickListener(object :
            CryptoListAdapter.OnItemClickListener {
            override fun onItemClick(cryptoId: String) {
                openCryptoById(cryptoId)
            }
        })
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isInternetAvailable()) {
                viewModel.getCryptoCurrencies(viewModel.currentCurrency)
            } else {
                Snackbar.make(binding.root, ERROR_MESSAGE, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(requireContext().getColor(R.color.crypto_percent_text_color_red))
                    .show()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initChipGroupListener() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.chipUsd -> {
                    viewModel.currentCurrency = USD_CURRENCY
                    viewModel.updateCache(viewModel.currentCurrency)
                }

                R.id.chipRub -> {
                    viewModel.currentCurrency = RUB_CURRENCY
                    viewModel.updateCache(viewModel.currentCurrency)
                }
            }
        }
    }

    private fun openCryptoById(cryptoId: String) {
        findNavController().navigate(
            CryptoListFragmentDirections.actionCryptoListFragmentToCryptoDetailsFragment(
                cryptoId
            )
        )
    }

    private fun showErrorState() {
        binding.rvCryptoRecycler.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.btnTry.setOnClickListener {
            viewModel.getCryptoCurrencies(viewModel.currentCurrency)
        }
    }

    private fun showLoadingState() {
        binding.errorView.visibility = View.GONE
        binding.rvCryptoRecycler.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showContentState(cryptoList: List<CryptoCurrency>) {
        binding.rvCryptoRecycler.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        adapter.dataSet = cryptoList
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}