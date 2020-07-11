package com.ernestkoko.superpro.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ernestkoko.superpro.firebase.FirebaseProducts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

//Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ProductRepository(private val productDao: ProductsDao) {
    //instantiate and initialise the firebase database
    private val database = Firebase.database.reference
    private val auth = FirebaseAuth.getInstance()

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val getAllProducts: LiveData<List<Product>> = productDao.getAllProductsById()


    val fbProductList = ArrayList<FirebaseProducts>()
    fun getFbProducts(): MutableLiveData<ArrayList<FirebaseProducts>> {
        loadProducts()
        val products = MutableLiveData<ArrayList<FirebaseProducts>>()
        products.value = fbProductList
        return products
    }

    private fun loadProducts() {
        val fbRef = Firebase.database.reference
        val query = fbRef.orderByKey().equalTo(FirebaseAuth.getInstance().uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (shot in snapshot.children) {
                    fbProductList.add(shot.getValue(FirebaseProducts::class.java)!!)
                }
            }

        })
    }


    //insert method to the firebase db
    fun insertIntoFirebase(firebaseProducts: FirebaseProducts) {
        //insert the product into firebase database
        database.child("products").child(auth.currentUser!!.uid).push().setValue(firebaseProducts)

    }

    //get product details from firebase database
    fun getProductDetailsFromFb() {
        val query = database.child("products").child(auth.currentUser!!.uid).orderByKey()

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (singleSnapshot in snapshot.children) {
                    var product1 = singleSnapshot.getValue(FirebaseProducts::class.java)

                    val product = Product(
                        1, product1!!.name, product1.image_url, 2,
                        product1.expiry_date, product1.manufacturer
                    )

                    val job = Job()
                    var uiScope = CoroutineScope(Dispatchers.Main + job)
                    uiScope.launch {
                        insert(product)
                    }
                    // insert(product)
                }
            }
        })

    }

    //insert
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }

    //get product by id
    fun getProductById(key: Long): LiveData<Product> {
        return productDao.getProductById(key)
    }

    fun getProductByName(): LiveData<List<Product>> {
        return productDao.getProductsByName()
    }

    //delete by id
    fun deleteById(key: Long) {
        productDao.deleteById(key)
    }


}