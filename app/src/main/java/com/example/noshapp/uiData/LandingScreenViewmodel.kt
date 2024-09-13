package com.example.noshapp.uiData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noshapp.model.Dish
import com.example.noshapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class LandingScreenViewmodel : ViewModel() {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> get() = _dishes


    private val apiService = RetrofitInstance.api

    private val _selectedDish = MutableLiveData<Dish?>()
    val selectedDish: LiveData<Dish?> get() = _selectedDish

    private val _selectedHour = MutableLiveData<Int?>()
    val selectedHour: LiveData<Int?> get() = _selectedHour

    private val _selectedMinute = MutableLiveData<Int?>()
    val selectedMinute: LiveData<Int?> get() = _selectedMinute

    private val _selectedAmPm = MutableLiveData<String?>()
    val selectedAmPm: LiveData<String?> get() = _selectedAmPm


    init {
        fetchDishes()
    }

    private fun fetchDishes() {
        viewModelScope.launch {
            try {
                val dishesList = apiService.getDishes()
                _dishes.value = dishesList
                Log.e("Dishes", dishesList.toString())
            } catch (e: Exception) {
                _dishes.value = emptyList()
                Log.e("Error fetching dishes", e.toString())
            }
        }
    }

    fun selectDish(dish: Dish) {
        _selectedDish.value = dish
    }

    fun setTime(hour: Int?, minute: Int?, amPm: String?) {
        _selectedHour.value = hour
        _selectedMinute.value = minute
        _selectedAmPm.value = amPm
    }

    fun getSelectedDishImageUrl(): String? {
        return _selectedDish.value?.imageUrl
    }
}

