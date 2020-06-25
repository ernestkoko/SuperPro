package com.ernestkoko.superpro


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController

import androidx.navigation.ui.AppBarConfiguration

import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navController = findNavController(R.id.nav_host_fragment)
//        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer)
//        val toolBar = findViewById<Toolbar>(R.id.product_tool_bar)
//        toolBar.setupWithNavController(navController, appBarConfiguration)
//       findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }
}
