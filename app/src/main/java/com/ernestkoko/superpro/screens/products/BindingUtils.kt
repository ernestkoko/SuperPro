package com.ernestkoko.superpro.screens.products

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ernestkoko.superpro.data.Product

@BindingAdapter("productName")
fun TextView.setProductName(product: Product?){
    product?.let {
        text = product.productName
    }
}

@BindingAdapter("productManufacturer")
fun TextView.setProductManufacturer(product: Product?){
    product?.let {
        text = product.prodManufacturer
    }
}
@BindingAdapter("productExpiryDate")
fun TextView.setProductExpiryDate(product: Product?){
    product?.let {
        text = product.productExpiryDate.toString()
    }
}