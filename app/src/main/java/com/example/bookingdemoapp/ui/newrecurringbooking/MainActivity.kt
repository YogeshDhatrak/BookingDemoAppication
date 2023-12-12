package com.example.bookingdemoapp.ui.newrecurringbooking

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bookingdemoapp.databinding.ActivityMainBinding
import com.example.bookingdemoapp.ui.base.BaseActivity
import com.example.bookingdemoapp.utils.NetworkHelper
import com.example.bookingdemoapp.utils.Status
import com.example.bookingdemoapp.utils.enableDisableButton
import com.example.bookingdemoapp.utils.showDatePicker
import com.example.bookingdemoapp.utils.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var networkHelper: NetworkHelper
    private var isChildrenSelected = false
    private var isRoomSelected = false
    private var isDaySelected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
        if (networkHelper.isNetworkConnected()) {
            setUpChildrenObserver()
        } else {
            showAlert("No internet available,please try after some time")
        }
    }

    private fun setUpViews() {
        binding.apply {
            llWho.setOnClickListener {
                if (!llWhosGoing.isVisible) {
                    llWhosGoing.toggleVisibility(true)
                    llWho.toggleVisibility(false)
                }
            }
            llWhere.setOnClickListener {
                if (!llChooseRoom.isVisible) {
                    llChooseRoom.toggleVisibility(true)
                    llWhere.toggleVisibility(false)
                }
            }
            textStartDate.setOnClickListener {
                textStartDate.showDatePicker(this@MainActivity)
            }
            textEndDate.setOnClickListener {
                val startDate = binding.textStartDate.text.toString()
                textEndDate.showDatePicker(this@MainActivity, startDate)
            }
            chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                isDaySelected = checkedIds.size > 0
                enableDisableButton()
            }
            btnReviewBooking.setOnClickListener {
                showToast("Review Button Clicked")
            }
        }
        enableDisableButton()
    }

    private fun setUpChildrenObserver() {
        viewModel.fetchChildren()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.children.collect {
                    when (it) {
                        is Status.Success -> {
                            binding.progressBar.toggleVisibility(false)
                            Timber.e("setUpChildrenObserver Success->${it.data}")
                            if (it.data.isNotEmpty()) {
                                initChildrenRadioButton(it.data)
                            } else {
                                showAlert("No Data Found!")
                            }
                        }

                        is Status.Loading -> {
                            binding.progressBar.toggleVisibility(true)
                            Timber.e("setUpChildrenObserver Loading->${it}")
                        }

                        is Status.Error -> {
                            binding.progressBar.toggleVisibility(false)
                            Timber.e("setUpChildrenObserver Error->${it.message}")
                            showAlert(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun setUpRoomsObserver(availableRoomsId: String) {
        viewModel.fetchAvailableRooms(availableRoomsId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.availableRooms.collect {
                    when (it) {
                        is Status.Success -> {
                            binding.progressBar.toggleVisibility(false)
                            Timber.e("setRoomsObserver Success->${it.data}")
                            if (it.data.isNotEmpty()) {
                                initAvailableRoomRadioButton(it.data)
                            } else {
                                showAlert("No Data Found!")
                            }
                        }

                        is Status.Loading -> {
                            binding.progressBar.toggleVisibility(true)
                            Timber.e("setRoomsObserver Loading->${it}")
                        }

                        is Status.Error -> {
                            binding.progressBar.toggleVisibility(false)
                            Timber.e("setRoomsObserver Error->${it.message}")
                            showAlert(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun initChildrenRadioButton(children: List<ChildrenDetailsModel>) {
        binding.rgChildren.removeAllViews()
        for (i in children.indices) {
            val radioButton = RadioButton(this)
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            radioButton.text = children[i].fullName
            radioButton.id = i

            binding.let {
                it.rgChildren.apply {
                    addView(radioButton)
                    setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                        val rb = findViewById<View>(checkedId) as RadioButton
                        it.textWho.text = rb.text
                        it.llWho.toggleVisibility(true)
                        it.llWhosGoing.toggleVisibility(false)
                        enableDisableButton()
                        children[i].availableRoomsId?.let {ids->
                            setUpRoomsObserver(ids)
                        }
                    })
                }
            }
        }
    }

    private fun initAvailableRoomRadioButton(rooms: List<BookingRoomsModel>) {
        binding.rgChooseRoom.removeAllViews()
        for (i in rooms.indices) {
            val radioButton = RadioButton(this)
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.text = rooms[i].roomName
            radioButton.id = i

            binding.let {
                it.rgChooseRoom.apply {
                    addView(radioButton)
                    setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                        val rb = findViewById<View>(checkedId) as RadioButton
                        it.textWhere.text = rb.text
                        it.llWhere.toggleVisibility(true)
                        it.llChooseRoom.toggleVisibility(false)
                        enableDisableButton()
                    })
                }
            }
        }
    }

    private fun enableDisableButton() {
        binding.apply {
            isChildrenSelected = rgChildren.checkedRadioButtonId != -1
            isRoomSelected = rgChooseRoom.checkedRadioButtonId != -1
            //isDaySelected = chipGroup.checkedChipId != -1
            val enableButton =
                isChildrenSelected && isRoomSelected && isDaySelected && isStartDateSelected && isEndDateSelected
            Timber.e(
                "enableDisableButton :\nisChildrenSelected:$isChildrenSelected" +
                        "\nisRoomSelected:$isRoomSelected " +
                        "\nisDaySelected:$isDaySelected" +
                        "\nisStartDateSelected:$isStartDateSelected" +
                        "\nisEndDateSelected:$isEndDateSelected"
            )
            btnReviewBooking.enableDisableButton(enableButton)
        }
    }


    companion object {
        var isStartDateSelected = false
        var isEndDateSelected = false
    }


}