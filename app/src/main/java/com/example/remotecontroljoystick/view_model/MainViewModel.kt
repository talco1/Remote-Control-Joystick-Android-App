package com.example.remotecontroljoystick.view_model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remotecontroljoystick.model.Model

class MainViewModel : ViewModel(), Observable {

    //ip and port variables bind to the EditText views
    @Bindable
    val ipAddress = MutableLiveData<String>()
    @Bindable
    val port = MutableLiveData<String>()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    /* When the connect button is pressed, activate the model's connect
     function if the ip and port were entered */
    fun connectFlightGear(): Boolean {
        if(ipAddress.value != null && port.value != null){
            Model.connect(ipAddress.value.toString(), (port.value.toString()).toInt())
            return true
        }
        return false
    }

    //notify the model when a variable's value changes
    fun setRudder(rudder: Double) {
        Model.rudderChanged(rudder)
    }
    fun setThrottle(throttle: Double) {
        Model.throttleChanged(throttle)
    }

    fun setAileron(aileron: Double) {
        Model.aileronChanged(aileron)
    }
    fun setElevator(elevator: Double) {
        Model.elevatorChanged(elevator)
    }

}


