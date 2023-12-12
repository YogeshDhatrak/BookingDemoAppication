package com.example.bookingdemoapp.ui.base


import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookingdemoapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity : AppCompatActivity() {


    open fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    open fun showAlert(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(message)
            .setPositiveButton(getString(R.string.label_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}