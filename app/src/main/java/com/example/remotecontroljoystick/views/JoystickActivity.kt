package com.example.remotecontroljoystick.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
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

        var rx=0F
        var ry=0F
        val observer: ViewTreeObserver = imageView.viewTreeObserver
        observer.addOnGlobalLayoutListener { rx= (rudder.width-throttle.height-imageView.width).toFloat()
            ry= (throttle.width-imageView.height).toFloat()
            imageView.x=(rudder.x+throttle.height)+rx/2
            imageView.y=(rudder.y-throttle.width)+ry/2
        }

        imageView.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    xJoystick = event.x
                    yJoystick = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val newY = imageView.y + (event.y - yJoystick)
                    val newX = imageView.x + (event.x - xJoystick)
                    val startXRange = rudder.x + throttle.height
                    val startYRange = rudder.y - throttle.width
                    val endXRange = rudder.x + rudder.width - imageView.width
                    val endYRange = rudder.y - imageView.height
                    //keep within boundaries
                    if ((newX in startXRange..endXRange) && (newY in startYRange..endYRange)) {
                        imageView.x = newX
                        imageView.y = newY
                        val aileron = ((imageView.x - startXRange)/(endXRange-startXRange))*2-1
                        viewModel.setAileron(aileron.toDouble())
                        var elevator = ((imageView.y - startYRange)/(endYRange-startYRange))*2-1
                        viewModel.setElevator(elevator.toDouble())
                    }
                }
            }
            true
        })
    }

}