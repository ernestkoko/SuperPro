package com.ernestkoko.superpro.screens.newproducts

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ernestkoko.superpro.data.Product
import com.ernestkoko.superpro.data.ProductDatabase
import com.ernestkoko.superpro.data.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewProductViewModel(
    application: Application
) : AndroidViewModel(application) {
    //get reference to the db dao
    //private val dataSource: ProductsDao
    //reference to the repository
    private val repository: ProductRepository

    // live data of name
    private var _prodName : String

    //setters for the parameters
    fun setName(name: String){
    _prodName = name
    Log.i("NewProductName", name )
}


    // prodQuantity
    private var _prodQuantity: Long
    fun setProdQuantity(number: Long?){
        _prodQuantity = number!!

    }


    //product manufacturer
    private var _prodManufacturer : String
    fun setProdManufacturer(manufacturer: String){
        _prodManufacturer = manufacturer
    }




    // product Expiry Date
    private var _prodExpiryDate: Int? = null
    private var _month: Int? = null
    private var _year: Int? = null
    fun setProdExpiryDate(date: Int, month: Int, year: Int){
        _prodExpiryDate = date
        _month = month
        _year = year

    }
    //set edit text

    var setEditText = MutableLiveData<String>()





    init {
        //get instance of the dao and initialise it
        val productDao = ProductDatabase.getDataBase(application).productDao()
        //initialise the repository
        repository = ProductRepository(productDao)
        // dataSource = ProductDatabase.getDataBase(getApplication()).productDao()
        _prodName = "Not Set"
        _prodManufacturer = "Not Set"
        _prodQuantity = 1
       // _prodExpiryDate = System.currentTimeMillis()
    }

    //create a job
    private var viewModeJob = Job()

    //create a scope to launch coroutine on the ui thread
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)


    private fun insert(product: Product) = viewModelScope.launch(Dispatchers.IO) {
//
//        Product(0,_prodName.toString(), prodQuantity.toString().toLong(),
//        _prodExpiryDate.toString().toLong(), 0.toString()
//        )
        Log.i("Insert", product.productName)
        repository.insert(product)
    }
    //called when the submit button is clicked in the fragment layout xml
    fun onInsert(){
        val expDate = convertIntToDate(_prodExpiryDate!!, _month!!, _year!! )
//        val product = Product(0, _prodName, _prodQuantity.toString().toLong(),
//        expDate, _prodManufacturer)
//
//       // insert into db
//        insert(product)
        Log.i("NewProdVMInsert", "onInsertFired")
    }
    //convert Long to time
    private fun convertIntToDate(day: Int, month: Int, year: Int): Date {
        var date:Date
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)
        val inputFormat = SimpleDateFormat("YY.MM.dd")
        date = inputFormat.parse(calendar.time.toString())

        //val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val format = SimpleDateFormat("yyyy/MM/dd")
        format.format(date)
        Log.i("DateFormat:", date.toString() )
        return date
    }

    // set the text to the date edit text
    fun setDateToEditText(date: String?){

        setEditText.value = date

    }
    // save product image to an external file
    fun saveImageToExternalFile(uri: Uri?): String{


        return ""
    }

}