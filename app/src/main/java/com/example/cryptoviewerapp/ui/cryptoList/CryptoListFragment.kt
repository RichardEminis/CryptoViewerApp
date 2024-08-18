package com.example.cryptoviewerapp.ui.cryptoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoviewerapp.databinding.FragmentCryptoListBinding
import com.example.cryptoviewerapp.model.CryptoCurrency
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

        viewModel.cryptoCurrencies.observe(viewLifecycleOwner) { cryptoUiState ->
            when {
                cryptoUiState.cryptocurrency == null -> showErrorState()
                cryptoUiState.cryptocurrency.isEmpty() -> showEmptyState()
                else -> showContentState(cryptoUiState.cryptocurrency)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
            viewModel.getCryptoCurrencies(viewModel.currentCurrency)
            binding.swipeRefreshLayout.isRefreshing = false
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
    }

    private fun showEmptyState() {
        binding.rvCryptoRecycler.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showContentState(cryptoList: List<CryptoCurrency>) {
        binding.rvCryptoRecycler.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
        adapter.dataSet = cryptoList
    }
}