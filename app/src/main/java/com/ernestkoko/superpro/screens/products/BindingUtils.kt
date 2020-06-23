package com.ernestkoko.superpro.screens.products

import android.content.Intent
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.squareup.picasso.Picasso

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
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, product: Product?) {
    product?.let {
        Picasso.get().load(product.productImage!!).fit().into(imageView)
    }
//    imgUrl?.let {
//      val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        //use glide to load the image
//        Glide.with(imgView.context)
//            .load(gallery)
//            .apply(
//                RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_add))
//            .into(imgView)
//    }
}