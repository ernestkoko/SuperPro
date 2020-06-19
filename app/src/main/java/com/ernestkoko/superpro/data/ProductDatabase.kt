package com.ernestkoko.superpro.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
 abstract class ProductDatabase: RoomDatabase(){
    //get the dao associated with it
    abstract fun productDao(): ProductsDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time. Volatile ensure the db is always up to date
        @Volatile
        private var INSTANCE: ProductDatabase? = null
        fun getDataBase(context: Context): ProductDatabase{

           return INSTANCE?: synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "products_database.db"
                ).fallbackToDestructiveMigration()
//                    .addCallback(ProductDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                //return the instance
                return instance

            }
        }
    }

//    private class ProductDatabaseCallBack(
//        val scope: CoroutineScope
//    ):RoomDatabase.Callback(){
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var productDao = database.productDao()
//
//                    //delete all content
//                    productDao.deleteAll()
//
//                    //add sample products
//                    var product = Product(1, "ToothBrush", "Macleans")
//                    productDao.insert(product);
//                }
//            }
//        }
//    }

}