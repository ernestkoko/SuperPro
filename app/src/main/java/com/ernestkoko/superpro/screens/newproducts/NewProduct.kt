package com.ernestkoko.superpro.screens.newproducts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.ernestkoko.superpro.R

class NewProduct : AppCompatActivity() {
    private lateinit var prodName: EditText
    private lateinit var prodManu: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        prodManu = findViewById(R.id.prod_manufacturer_edit);
        prodName = findViewById(R.id.prod_name_edit);

        val button = findViewById<Button>(R.id.submit_product);
        button.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(prodName.text) || TextUtils.isEmpty(prodManu.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else
            {
               val prod_name =  prodManu.text.toString()
                val prodManufacturer = prodManu.text.toString()
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }
    companion object {
        const val EXTRA_REPLY = "com.ernestkoko.superpro.REPLY"
    }
}
