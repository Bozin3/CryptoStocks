package com.bozin3.cryptostocks.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bozin3.cryptostocks.R

class CryptoStocksFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoStocksFragment()
    }

    private lateinit var viewModel: CryptoStocksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.crypto_stocks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CryptoStocksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
