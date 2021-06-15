package com.example.remotecontroljoystick.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.remotecontroljoystick.R
import com.example.remotecontroljoystick.databinding.ActivityMainBinding
import com.example.remotecontroljoystick.view_model.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var databinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        databinding.viewmodel = viewModel
        databinding.lifecycleOwner = this
        var b = findViewById<Button>(R.id.button)
        b.setOnClickListener{
            if(viewModel.connectFlightGear()){
                var intent = Intent(this, JoystickActivity::class.java)
                startActivity(intent)
            }
        }


    }
}

