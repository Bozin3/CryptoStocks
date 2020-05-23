package com.bozin3.cryptostocks.ui

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bozin3.cryptostocks.databinding.CryptoDetailsFragmentBinding
import com.bozin3.cryptostocks.viewmodels.CryptoDetailsViewModel
import com.bozin3.cryptostocks.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CryptoDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val selectedCrypto = CryptoDetailsFragmentArgs.fromBundle(arguments!!).coinId

        val binding = CryptoDetailsFragmentBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)[CryptoDetailsViewModel::class.java]
        viewModel.getCryptoData(selectedCrypto).observe(viewLifecycleOwner, Observer { cryptoData ->
            binding.crypto = cryptoData
            binding.executePendingBindings()
        })

        return binding.root
    }

}
