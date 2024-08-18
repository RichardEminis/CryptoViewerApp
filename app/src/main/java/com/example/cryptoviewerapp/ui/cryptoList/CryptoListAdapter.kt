package com.example.cryptoviewerapp.ui.cryptoList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoviewerapp.databinding.ItemCryptoBinding
import com.example.cryptoviewerapp.model.CryptoCurrency
import javax.inject.Inject
import com.bumptech.glide.Glide
import com.example.cryptoviewerapp.R

class CryptoListAdapter @Inject constructor(
    private var itemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<CryptoListAdapter.ViewHolder>() {

    var dataSet: List<CryptoCurrency> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(cryptoId: String)
    }

    class ViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(crypto: CryptoCurrency, clickListener: OnItemClickListener?) {
            binding.tvCryptoName.text = crypto.name
            binding.tvCryptoAbbreviation.text = crypto.symbol
            binding.tvCryptoPrice.text = crypto.currentPrice.toString()
            binding.tvPercentageText.text = crypto.changePercentage.toString()
            binding.cvCryptoItem.setOnClickListener {
                clickListener?.onItemClick(crypto.id)
            }

            Glide.with(itemView.context)
                .load(crypto.image ?: R.drawable.btc) //Необходимо найти изображение ошибки загрузки
                .error(R.drawable.btc)
                .into(binding.ivCryptoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crypto = dataSet[position]
        holder.bind(crypto, itemClickListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}