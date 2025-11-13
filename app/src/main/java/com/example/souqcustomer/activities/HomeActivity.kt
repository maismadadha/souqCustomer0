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
import com.example.souqcustomer.fragments.categoriesFragment
import com.example.souqcustomer.fragments.favouriteFragment
import com.example.souqcustomer.fragments.homeFragment
import com.example.souqcustomer.fragments.orderFragment
import com.example.souqcustomer.fragments.profileFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("userId", 0)
        val bundle = Bundle().apply { putInt("userId", userId) }
        val hf = homeFragment().apply { arguments = bundle }
        val of= orderFragment().apply { arguments = bundle }
        val pf = profileFragment().apply { arguments = bundle }
        val ff= favouriteFragment().apply { arguments = bundle }
        val cf= categoriesFragment().apply { arguments = bundle }










        binding.btnNav.setItemIconTintList(null)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment)as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.btnNav
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
}