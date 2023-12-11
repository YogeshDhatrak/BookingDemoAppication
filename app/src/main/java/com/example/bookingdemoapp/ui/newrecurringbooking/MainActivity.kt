package com.example.bookingdemoapp.ui.newrecurringbooking

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bookingdemoapp.databinding.ActivityMainBinding

import com.example.bookingdemoapp.ui.base.BaseActivity
import com.example.bookingdemoapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity(){
    private lateinit var binding: ActivityMainBinding
    private val  viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.children.collect {
                    when (it) {
                        is Status.Success -> {
                            binding.progressBar.visibility = View.GONE
                            showAlert("Fetch Data Successfully")
                        }
                        is Status.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Status.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            showAlert(it.message)
                        }
                    }
                }
            }
        }
    }
}