package com.ernestkoko.superpro.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "products_name")
    var productName: String?,
    @ColumnInfo(name = "manufacturer")
    var prodManufacturer: String?

)