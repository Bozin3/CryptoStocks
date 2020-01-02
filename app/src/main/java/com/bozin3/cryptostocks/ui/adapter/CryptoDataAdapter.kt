package com.bozin3.cryptostocks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bozin3.cryptostocks.databinding.CryptoListItemBinding
import com.bozin3.cryptostocks.models.CryptoDomainModel

class CryptoDataAdapter : ListAdapter<CryptoDomainModel, CryptoDataAdapter.CryptoViewHolder >(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(CryptoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) =  holder.bind(getItem(position))

    class CryptoViewHolder (
        private var binding: CryptoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cryptoDomainModel: CryptoDomainModel) {
            binding.cryptoDomainModel = cryptoDomainModel
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<CryptoDomainModel>() {
        override fun areItemsTheSame(oldItem: CryptoDomainModel, newItem: CryptoDomainModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CryptoDomainModel, newItem: CryptoDomainModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}