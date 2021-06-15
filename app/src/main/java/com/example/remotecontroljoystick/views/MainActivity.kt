package com.example.remotecontroljoystick.views

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)
        databinding.viewmodel = viewModel
        databinding.lifecycleOwner = this
    }
}