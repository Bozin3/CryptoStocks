package com.bozin3.cryptostocks.ui

import android.content.Context
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
import com.bozin3.cryptostocks.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class CryptoStocksFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: CryptoStocksViewModel

    private var cryptoDataAdapter: CryptoDataAdapter? = null

    private val mHandler  by lazy {
        Handler()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CryptoStocksFragmentBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)

        cryptoDataAdapter = CryptoDataAdapter(OnItemClickListener {
            // Navigate to details fragment
            val direction = CryptoStocksFragmentDirections.actionCryptoStocksFragmentToCryptoDetailsFragment(it.id)
            findNavController().navigate(direction)
        })

        binding.cryptoDataRecyclerView.adapter = cryptoDataAdapter

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CryptoStocksViewModel::class.java]

        viewModel.cryptoData.observe(this, Observer { cryptoData ->
            cryptoDataAdapter?.submitList(cryptoData)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading ->
            binding.progressBar.visibility = if(isLoading) {
                View.VISIBLE
            }else{
                View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->

            Timber.e("Error: $errorMessage")
            Toast.makeText(activity, getString(R.string.network_error), Toast.LENGTH_LONG).show()
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

    override fun onDestroyView() {
        cryptoDataAdapter = null
        super.onDestroyView()
    }

}
