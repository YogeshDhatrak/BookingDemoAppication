package com.example.bookingdemoapp.ui.newrecurringbooking

import android.os.Bundle
import com.example.bookingdemoapp.databinding.ActivityMainBinding
import com.example.bookingdemoapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}