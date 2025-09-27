package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNav.setItemIconTintList(null)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment)as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.btnNav
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
}