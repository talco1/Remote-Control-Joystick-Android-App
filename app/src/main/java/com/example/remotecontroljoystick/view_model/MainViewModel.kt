package com.example.remotecontroljoystick.view_model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotecontroljoystick.model.Model

class MainViewModel : ViewModel(), Observable {

    @Bindable
    val ipAddress = MutableLiveData<String>()
    @Bindable
    val port = MutableLiveData<String>()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    fun connectFlightGear() = Model.connect(ipAddress.value.toString(), (port.value.toString()).toInt())

    fun rudder() = Model.rudderChanged()
}
