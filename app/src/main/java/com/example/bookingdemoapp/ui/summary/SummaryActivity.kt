package com.example.bookingdemoapp.ui.summary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookingdemoapp.R
import com.example.bookingdemoapp.databinding.ActivitySummaryBinding
import com.example.bookingdemoapp.utils.getDuration
import com.example.bookingdemoapp.utils.getSpannableString
import timber.log.Timber

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding
    lateinit var summaryData: SummaryData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        setUpViews()
    }


    private fun getIntentData() {
        summaryData = intent.getParcelableExtra<SummaryData>("summaryData")!!
        Timber.d("getIntentData: ${summaryData}")
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        binding.apply {
            textSelectedFor.text = summaryData.childrenName
            textSelectedRoom.text = summaryData.roomName
            textSelectedFrom.text = getString(R.string.label_from,summaryData.startDate)
            textSelectedDays.text = getString(R.string.label_every,summaryData.selectedDays)
            textSelectedUpto.text = getString(R.string.label_end,summaryData.endDate)

            val duration= getDuration(summaryData.startDate,summaryData.endDate)
            textSelectedDaysCount.text = getString(R.string.label_day_count,duration)
                .getSpannableString(16,19)
            textBookingReferenceNumber.text =getString(R.string.label_booking_reference,summaryData.bookingReferenceNo)
                .getSpannableString(0,25)

            btnDone.setOnClickListener {
                finishAffinity()
            }
            btnChangeBooking.setOnClickListener {
                finish()
            }
        }
    }
}