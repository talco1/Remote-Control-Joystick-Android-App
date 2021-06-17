package com.example.remotecontroljoystick.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.remotecontroljoystick.R
import com.example.remotecontroljoystick.databinding.ActivityMainBinding
import com.example.remotecontroljoystick.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        //bind the viewmodel variable of activity_main to MainViewModel
        dataBinding.viewmodel = viewModel
        dataBinding.lifecycleOwner = this

        var connectButton = findViewById<Button>(R.id.button)
        connectButton.setOnClickListener{
            //when the button is clicked, connect to flightGear and start the joystick activity
            if(viewModel.connectFlightGear()){
                var intent = Intent(this, JoystickActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

