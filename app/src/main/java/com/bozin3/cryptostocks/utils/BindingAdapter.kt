package com.bozin3.cryptostocks.utils

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bozin3.cryptostocks.R

@BindingAdapter("price")
fun setPrice(tvPrice: TextView, price: Float) {

    //if the price is big number then we dont care about the decimals etc etc
    val formatPrice = if(price < 9){
        String.format("%.6f", price)
    }else if(price < 99 && price > 9){
        String.format("%.4f", price)
    }else if(price < 9999 && price > 99 ){
        String.format("%.2f", price)
    }else if(price < 99999 && price > 9999 ){
        String.format("%.1f", price)
    }else{
        String.format("%.2f", price)
    }

    tvPrice.text = "$formatPrice$"
}

@BindingAdapter("percent")
fun setPercent(tvPercentage: TextView, percent: Float) {

    val formatPercentage = String.format("%.2f", percent)

    //if the percentage is negative we make the text color red, if positive then green
    val color = if(percent < 0){
        ContextCompat.getColor(tvPercentage.context.applicationContext, R.color.negativePercent)
    }else{
        ContextCompat.getColor(tvPercentage.context.applicationContext, R.color.positivePercent)
    }

    tvPercentage.text = "$formatPercentage%"
    tvPercentage.setTextColor(color)
}

@BindingAdapter("background")
fun setBackground(view: View, percent: Float) {

    //if the percentage is negative we make the text color red, if positive then green
    val color = if(percent < 0){
        ContextCompat.getColor(view.context.applicationContext, R.color.negativePercent)
    }else{
        ContextCompat.getColor(view.context.applicationContext, R.color.positivePercent)
    }

    view.setBackgroundColor(color)
}

@BindingAdapter("market_cap")
fun setMarketCap(tvMarketCap: TextView, marketCap: Float?) {

    val marketCapStr = marketCap?.toString()?:""
    tvMarketCap.text = tvMarketCap.context.applicationContext.getString(R.string.market_cap,marketCapStr)

}

@BindingAdapter("supply")
fun setSupply(tvSupply: TextView, supply: Float?) {

    val supplyStr = supply?.toString()?:""
    tvSupply.text = supplyStr

}


