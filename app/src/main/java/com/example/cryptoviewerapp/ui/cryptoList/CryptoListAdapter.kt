package com.example.cryptoviewerapp.ui.cryptoList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoviewerapp.R
import com.example.cryptoviewerapp.databinding.ItemCryptoBinding
import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.ulils.USD_CURRENCY
import javax.inject.Inject
import kotlin.math.abs

class CryptoListAdapter @Inject constructor(
    private var itemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<CryptoListAdapter.ViewHolder>() {

    var dataSet: List<CryptoCurrency> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var currentCurrency: String = USD_CURRENCY

    interface OnItemClickListener {
        fun onItemClick(cryptoId: String)
    }

    class ViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(crypto: CryptoCurrency, clickListener: OnItemClickListener?,currency: String) {
            binding.tvCryptoName.text = crypto.name
            binding.tvCryptoAbbreviation.text = crypto.symbol

            val currencySymbol = if (currency == USD_CURRENCY) "$" else "₽"
            binding.tvCryptoPrice.text = "$currencySymbol ${crypto.currentPrice}"

            val formattedPercentage = String.format(
                "%s %.2f%%",
                if (crypto.changePercentage >= 0) "+" else "-",
                abs(crypto.changePercentage)
            )

            binding.tvPercentageText.text = formattedPercentage

            if (crypto.changePercentage >= 0) {
                binding.tvPercentageText.setTextColor(binding.root.context.getColor(R.color.crypto_percent_text_color_green))
            } else {
                binding.tvPercentageText.setTextColor(binding.root.context.getColor(R.color.crypto_percent_text_color_red))
            }

            binding.cvCryptoItem.setOnClickListener {
                clickListener?.onItemClick(crypto.id)
            }

            Glide.with(itemView.context)
                .load(crypto.image ?: R.drawable.btc)
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
        holder.bind(crypto, itemClickListener, currentCurrency)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}