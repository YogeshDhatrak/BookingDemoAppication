package com.example.bookingdemoapp.ui.base

import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bookingdemoapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity:AppCompatActivity() {


    open fun showAlert(message: String){
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(message)
            .setPositiveButton(getString(R.string.label_ok)){dialog,_->
             dialog.dismiss()
            }
            .show()
    }
}