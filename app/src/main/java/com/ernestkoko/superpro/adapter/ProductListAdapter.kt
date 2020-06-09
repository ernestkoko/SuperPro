package com.ernestkoko.superpro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.databinding.ProductRecyclerItemBinding

// ListAdapter takes care of the list.
//It figures out the getItemCount also
class ProductListAdapter(
    val clickListener: ProductClickListener
) : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ProductsDiffCallback()) {
    // val inflater: LayoutInflater = LayoutInflater.from(context)
    private var products = emptyList<Product>() //cached copy of products

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        //let the binding class know about the click listener
        holder.bind(getItem(position)!!, clickListener)
        //getItem() is provided by ListAdapter
        val currentProduct = getItem(position)

        holder.bind(currentProduct, clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder.from(parent)
    }


    internal fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }


    //The view holder class
    class ProductViewHolder private constructor(val binding: ProductRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // val productImage: ImageView = binding.productImage
        //val productName: TextView = itemView.findViewById(R.id.product_name)

        //responsible for binding the views
        fun bind(
            currentProduct: Product,
            clickListener: ProductClickListener
        ) {
            binding.product = currentProduct
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }


    }


}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        //returns true if the two items have same id, false otherwise
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {

        //checks if the contents of an item has changed
        return oldItem == newItem
    }

}

//click listener
class ProductClickListener(val clickListener: (productId: Long) -> Unit) {
    fun onClick(product: Product) = clickListener(product.id)
}
