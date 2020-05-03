package com.bozin3.cryptostocks.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bozin3.cryptostocks.databinding.CryptoDetailsFragmentBinding
import com.bozin3.cryptostocks.viewmodels.CryptoDetailsViewModel

class CryptoDetailsFragment : Fragment() {

    private lateinit var viewModel: CryptoDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val selectedCrypto = CryptoDetailsFragmentArgs.fromBundle(arguments!!).coinId

        viewModel = ViewModelProviders.of(this).get(CryptoDetailsViewModel::class.java)
        viewModel.getCryptoData(selectedCrypto)

        val binding = CryptoDetailsFragmentBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        binding.detailsViewModel = viewModel

        return binding.root
    }

}
