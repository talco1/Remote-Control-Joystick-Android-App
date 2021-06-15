package com.example.remotecontroljoystick.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.remotecontroljoystick.R
import com.example.remotecontroljoystick.view_model.MainViewModel

class JoystickActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    var xJoystick=0.0f
    var yJoystick=0.0f
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.joystick_activity)

        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        //seek bars
        var rudder = findViewById<SeekBar>(R.id.rudderSeekBar)
        rudder.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setRudder(progress.toDouble() / 100.0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //empty
            }
        })
        var throttle = findViewById<SeekBar>(R.id.throttleSeekBar)
        throttle.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setThrottle(progress.toDouble() / 100.0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //empty
            }
        })

        val imageView = findViewById<ImageView>(R.id.joystickImage)
        imageView.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xJoystick = event.x
                    yJoystick = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveX: Float
                    val moveY: Float
                    moveX = event.x
                    moveY = event.y
                    val distanceX: Float = moveX - xJoystick
                    val distanceY: Float = moveY - yJoystick
                    imageView.setX(imageView.getX() + distanceX)
                    imageView.setY(imageView.getY() + distanceY)
                }
            }
            true
        })
    }

}