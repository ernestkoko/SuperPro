package com.ernestkoko.superpro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ernestkoko.superpro.R
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.databinding.ProductRecyclerItemBinding
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
     class ProductViewHolder  private constructor (val binding: ProductRecyclerItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val productImage: ImageView = binding.productImage
        //val productName: TextView = itemView.findViewById(R.id.product_name)

        //responsible for binding the views
        fun bind(currentProduct: Product) {
            binding.product = currentProduct
            binding.executePendingBindings()
        }

        companion object {
            fun from( parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }


    }



}