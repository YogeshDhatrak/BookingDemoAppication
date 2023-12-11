package com.example.bookingdemoapp.ui.newrecurringbooking

import androidx.lifecycle.viewModelScope
import com.example.bookingdemoapp.data.model.Children
import com.example.bookingdemoapp.data.repository.NewRecurringBookingRepository
import com.example.bookingdemoapp.ui.base.BaseViewModel
import com.example.bookingdemoapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NewRecurringBookingRepository) :
    BaseViewModel() {
    private val _children = MutableStateFlow<Status<List<Children>>>(Status.Loading)

    val children: StateFlow<Status<List<Children>>> = _children

    init {
        //fetchChildren()
    }

    fun fetchChildren() {
        viewModelScope.launch {
            repository.getChildren().catch {e ->
                Timber.e("fetchChildren-->$e")
                _children.value = Status.Error(e.toString())
            }.collect {
                Timber.e("fetchChildren --> $it")
                _children.value = Status.Success(it)
            }
        }
    }
}