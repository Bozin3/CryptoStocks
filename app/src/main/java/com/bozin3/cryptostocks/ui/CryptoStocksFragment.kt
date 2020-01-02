package com.bozin3.cryptostocks.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer

import com.bozin3.cryptostocks.R
import com.bozin3.cryptostocks.databinding.CryptoStocksFragmentBinding
import com.bozin3.cryptostocks.ui.adapter.CryptoDataAdapter

class CryptoStocksFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoStocksFragment()
    }

    // This will be initialized when we invoke it for the first time
    private val viewModel: CryptoStocksViewModel by lazy {
        ViewModelProviders.of(this).get(CryptoStocksViewModel::class.java)
    }

    private lateinit var cryptoDataAdapter: CryptoDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CryptoStocksFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        cryptoDataAdapter = CryptoDataAdapter()
        binding.cryptoDataRecyclerView.adapter = cryptoDataAdapter

        viewModel.cryptoData.observe(this, Observer { cryptoData ->
            cryptoDataAdapter.submitList(cryptoData)
        })

        viewModel.error.observe(this, Observer { errorMessage ->

            val error = getString(R.string.error_message,errorMessage)
            Toast.makeText(this.activity,error,Toast.LENGTH_LONG).show()
        })

        return binding.root
    }

}
