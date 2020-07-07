package com.ernestkoko.superpro.screens.products

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.firebase.FirebaseProducts
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

    @BindingAdapter("fireProductName")
    fun TextView.setName(product: FirebaseProducts?){
        product?.let {
            text = product.name
        }
    }
    @BindingAdapter("fireProductManufacturer")
    fun TextView.setManufacturer(product: FirebaseProducts?){
        product?.let {
            text = product.manufacturer
        }
    }
    @BindingAdapter("firebaseProdExpDate")
    fun TextView.setExpDate(product: FirebaseProducts?){
        product?.let {
            text = product.expiry_date
        }
    }
}