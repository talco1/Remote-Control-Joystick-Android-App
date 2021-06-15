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
                    val newY = imageView.y + (event.y - yJoystick)
                    val newX = imageView.x + (event.x - xJoystick)
                    val xRange = rudder.width - throttle.height
                    val yRange = throttle.width
                    //keep within boundaries
                    if (((newX+imageView.width) <= (rudder.x + rudder.width)) && ((newY+imageView.height) <= rudder.y)
                        && (newX >= (rudder.x + throttle.height)) && newY >= (rudder.y - throttle.width)) {
                        imageView.x = newX
                        imageView.y = newY
                        val aileron = (imageView.x/xRange)*2-2
                        println("aileron: "+aileron)
                        println("original ail: "+imageView.x)
                        println("x range: $xRange")
                        viewModel.setAileron(aileron.toDouble())
                        var elevator = (imageView.y/yRange)*2-2
                        viewModel.setElevator(elevator.toDouble())
                        println("elevator: "+elevator)
                        println("original ele: "+imageView.y)
                        println("y range: $yRange")


                        //rangeX: from (rudder.x + throttle.height) to (rudder.x + rudder.width)
                        // rudder.x + throttle.height = -1
                        // rudder.x + rudder.width = 1
                        // aileron(-1..1): x length = rudder.width - throttle.height


                        //rangeY: from (rudder.y - throttle.width) to (rudder.y)
                        // -1 = rudder.y - throttle.width
                        // 1 = rudder.y
                        //elevator (-1..1): y length = throttle.width
                    }
                }
            }
            true
        })
    }

}