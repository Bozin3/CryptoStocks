package com.bozin3.cryptostocks.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bozin3.cryptostocks.R

class CryptoDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoDetailsFragment()
    }

    private lateinit var viewModel: CryptoDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.crypto_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CryptoDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
