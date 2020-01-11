package com.bozin3.cryptostocks.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bozin3.cryptostocks.databinding.CryptoDetailsFragmentBinding
import com.bozin3.cryptostocks.viewmodels.CryptoDetailsViewModel
import com.bozin3.cryptostocks.viewmodels.DetailsViewModelFactory

class CryptoDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CryptoDetailsFragment()
    }

    private lateinit var viewModel: CryptoDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val selectedCrypto = CryptoDetailsFragmentArgs.fromBundle(arguments!!).coinId
        viewModel = ViewModelProviders.of(this,DetailsViewModelFactory(selectedCrypto,activity!!.application)).get(CryptoDetailsViewModel::class.java)

        val binding = CryptoDetailsFragmentBinding.inflate(inflater,container,false)
        binding.setLifecycleOwner(this)
        binding.detailsViewModel = viewModel

        return binding.root
    }

}
