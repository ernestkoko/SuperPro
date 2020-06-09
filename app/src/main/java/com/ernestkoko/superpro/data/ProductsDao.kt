package com.ernestkoko.superpro.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductsDao {
    //select all products from table order by name
    @Query("SELECT * FROM products_table ORDER BY  products_name ASC")
    fun getProductsByName(): LiveData<List<Product>>

    // insert a product in the table. suspend keeps the operation will when it can perform
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product )
    //update product
    @Update
    suspend fun update(product: Product)
    //get a product
    @Query("SELECT * FROM products_table ORDER BY id LIMIT 1")
    fun getAProduct(): Product

    //get all products by ID
    @Query("SELECT * FROM products_table ORDER BY id ASC")
    fun getAllProductsById(): LiveData<List<Product>>
    @Query("DELETE FROM products_table WHERE id= :key  ")
    fun deleteById(key: Long)

    //delete from product table
    @Query("DELETE FROM products_table")
    suspend fun deleteAll()
}