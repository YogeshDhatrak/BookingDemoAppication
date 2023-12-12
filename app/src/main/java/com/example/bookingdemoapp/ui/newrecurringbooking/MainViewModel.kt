package com.example.bookingdemoapp.ui.newrecurringbooking

import androidx.lifecycle.viewModelScope
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
    private val _children = MutableStateFlow<Status<List<ChildrenDetailsModel>>>(Status.Loading)

    val children: StateFlow<Status<List<ChildrenDetailsModel>>> = _children

    private val _availableRooms = MutableStateFlow<Status<List<BookingRoomsModel>>>(Status.Loading)

    val availableRooms: StateFlow<Status<List<BookingRoomsModel>>> = _availableRooms


    fun fetchChildren() {
        viewModelScope.launch {
            repository.getChildren().catch { e ->
                Timber.e("fetchChildren-->$e")
                _children.value = Status.Error(e.toString())
            }.collect {
                Timber.e("fetchChildren --> $it")
                _children.value = Status.Success(it)
            }
        }
    }

    fun fetchAvailableRooms(availableRoomsId: String) {
        viewModelScope.launch {
            repository.getAvailableRooms(availableRoomsId).catch { e ->
                Timber.e("fetchChildren-->$e")
                _availableRooms.value = Status.Error(e.toString())
            }.collect {
                Timber.e("fetchChildren --> $it")
                _availableRooms.value = Status.Success(it)
            }
        }
    }
}