package com.example.bookingdemoapp.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.bookingdemoapp.R
import com.example.bookingdemoapp.ui.newrecurringbooking.MainActivity
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


fun View.toggleVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

fun MaterialButton.enableDisableButton(isEnable: Boolean, isClickable: Boolean = true) {
    if (isEnable) {
        val activeColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context, R.color.btn_active
            )
        )
        strokeColor = activeColor
        setTextColor(
            ContextCompat.getColor(
                context, R.color.white
            )
        )
        this.isEnabled = true
        this.isClickable = isClickable
        this.alpha = 1f
    } else {
        val normalColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context, R.color.btn_normal
            )
        )
        strokeColor = normalColor
        setTextColor(
            ContextCompat.getColor(
                context, R.color.white
            )
        )
        this.isEnabled = false
        this.isClickable = false
        this.alpha = 0.5f
    }
}

@SuppressLint("SetTextI18n")
fun TextView.showDatePicker(context: Context, startDate: String = "") {
    val textView = this
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { view, year, monthOfYear, dayOfMonth ->
            when (textView.id) {
                R.id.text_start_date -> {
                    MainActivity.isStartDateSelected = true
                }
                R.id.text_end_date -> {
                    MainActivity.isEndDateSelected = true
                }
            }
            textView.text =
                "${dayOfMonth}/${monthOfYear + 1}/$year"
        },
        year,
        month,
        day
    )

    if (startDate.isNotEmpty()) {
        val defaultDate = startDate.split(Regex("/"))
        val dd = defaultDate[0].toInt()
        val mm = defaultDate[1].toInt() - 1
        val yy = defaultDate[2].toInt()
        datePickerDialog.updateDate(yy, mm, dd)
        c.set(yy, mm, dd)

    }
    datePickerDialog.datePicker.minDate = c.timeInMillis
    datePickerDialog.show()
}

fun String.getFormattedDate(): String {
    val date = this
    val dateFormat: Date = SimpleDateFormat("dd/MM/yyyy").parse(date)!!
    return SimpleDateFormat("dd MMM yyyy").format(dateFormat)
}

fun String.getSpannableString(start: Int, end: Int): SpannableString {
    val str: String = this
    val spannableString = SpannableString(str)
    val boldSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}
