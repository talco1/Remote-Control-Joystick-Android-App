package com.example.remotecontroljoystick.view_model

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotecontroljoystick.model.Model

class MainViewModel : ViewModel() {
    val ipAddress = MutableLiveData<String>()
}
