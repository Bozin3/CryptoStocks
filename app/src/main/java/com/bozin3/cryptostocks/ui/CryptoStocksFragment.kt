package com.bozin3.cryptostocks.ui

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bozin3.cryptostocks.R
import com.bozin3.cryptostocks.databinding.CryptoStocksFragmentBinding
import com.bozin3.cryptostocks.ui.adapter.CryptoDataAdapter
import com.bozin3.cryptostocks.ui.adapter.OnItemClickListener
import com.bozin3.cryptostocks.viewmodels.CryptoStocksViewModel
import com.bozin3.cryptostocks.viewmodels.StocksViewModelFactory
import timber.log.Timber


class CryptoStocksFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoStocksFragment()
    }

    // This will be initialized when we invoke it for the first time
    private val viewModel: CryptoStocksViewModel by lazy {
        ViewModelProviders.of(this,
            StocksViewModelFactory(activity!!.application))
            .get(CryptoStocksViewModel::class.java)
    }

    private lateinit var cryptoDataAdapter: CryptoDataAdapter

    private val mHandler  by lazy {
        Handler()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CryptoStocksFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        cryptoDataAdapter = CryptoDataAdapter(OnItemClickListener {
            // Navigate to details fragment
            val direction = CryptoStocksFragmentDirections.actionCryptoStocksFragmentToCryptoDetailsFragment(it.id)
            findNavController().navigate(direction)
        })

        binding.cryptoDataRecyclerView.adapter = cryptoDataAdapter

        viewModel.allData.observe(this, Observer { cryptoData ->
            cryptoDataAdapter.submitList(cryptoData)
        })

        viewModel.filteredData.observe(this, Observer {
            cryptoDataAdapter.submitList(it)
        })

        viewModel.error.observe(this, Observer { errorMessage ->

            val error = getString(R.string.error_message,errorMessage)
            Toast.makeText(this.activity,error,Toast.LENGTH_LONG).show()
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crypto_stocks_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setSubmitButtonEnabled(true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {

                mHandler.removeCallbacksAndMessages(null)

                // Search after the query is completed
                mHandler.postDelayed(Runnable {
                    viewModel.filterData(query)
                }, 300)

                return true
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.d("onQuerySubmit: $query")
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
