package com.bozin3.cryptostocks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bozin3.cryptostocks.databinding.CryptoListItemBinding
import com.bozin3.cryptostocks.localdb.entity.Crypto

class CryptoDataAdapter(val itemClickListener: OnItemClickListener<Crypto>)
    : ListAdapter<Crypto, CryptoDataAdapter.CryptoViewHolder >(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(CryptoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) =  holder.bind(getItem(position),itemClickListener)

    class CryptoViewHolder (
        private var binding: CryptoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(crypto: Crypto, itemClickListener: OnItemClickListener<Crypto>) {
            binding.cryptoData = crypto
            binding.itemClickListener = itemClickListener
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Crypto>() {
        override fun areItemsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crypto, newItem: Crypto): Boolean {
            return oldItem == oldItem
        }
    }
}

class OnItemClickListener<T>(val clickListener: (itemClicked:T) -> Unit) {
    fun onClick(itemClicked:T) = clickListener(itemClicked)
}