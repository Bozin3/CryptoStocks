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

    // This will be initialized when we invoke it for the first time
    private val viewModel: CryptoDetailsViewModel by lazy {
        ViewModelProviders.of(this,
            DetailsViewModelFactory(activity!!.application))
            .get(CryptoDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = CryptoDetailsFragmentBinding.inflate(inflater)

        return binding.root
    }

}
