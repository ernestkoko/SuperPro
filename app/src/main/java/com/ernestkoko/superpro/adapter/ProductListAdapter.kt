package com.ernestkoko.superpro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.screens.products.ProductsDiffCallback
// ListAdapter takes care of the list.
//It figures out the getItemCount also
class ProductListAdapter: ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ProductsDiffCallback()) {
   // val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<Product>() //cached copy of products

//        set(value) {
//            field = value
//            //triggers when data changes
//           // notifyDataSetChanged()
//        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder.from(parent)

    }

//    override fun getItemCount(): Int {
//
//        return products.size
//    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        //getItem() is provided by ListAdapter
        val currentProduct = getItem(position)

        holder.bind(currentProduct)
    }

    //extension function
//    private fun ProductViewHolder.bind(currentProduct: Product) {
//        productName?.text = currentProduct.productName
//       productManufacturer?.text = currentProduct.prodManufacturer
//    }

    internal fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }


    //The view holder class
     class ProductViewHolder  private constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productManufacturer: TextView = itemView.findViewById(R.id.prod_manufacturer)
        //val productName: TextView = itemView.findViewById(R.id.product_name)

        //responsible for binding the views
        fun bind(currentProduct: Product) {
            productName?.text = currentProduct.productName
            productManufacturer?.text = currentProduct.prodManufacturer
        }

        companion object {
            fun from( parent: ViewGroup): ProductViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_recycler_item, parent, false)
                return ProductViewHolder(view)
            }
        }


    }



}