package com.example.remotecontroljoystick.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.remotecontroljoystick.R
import com.example.remotecontroljoystick.view_model.MainViewModel

class JoystickActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    //save last location of the joystick
    private var xJoystick=0.0f
    private var yJoystick=0.0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.joystick_activity)

        viewModel = ViewModelProviders.of(this)
            .get(MainViewModel::class.java)

        //set a new rudder value when the rudder's seekbar progress changes
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

        //set a new throttle value when the throttle's seekbar progress changes
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

        val joystick = findViewById<ImageView>(R.id.joystickImage)
        //save the joystick's motion range
        var xRange=0F
        var yRange=0F
        //set the joystick initial location in the middle of its range
        val observer: ViewTreeObserver = joystick.viewTreeObserver
        observer.addOnGlobalLayoutListener { xRange= (rudder.width-throttle.height-joystick.width).toFloat()
            yRange= (throttle.width-joystick.height).toFloat()
            joystick.x=(rudder.x+throttle.height)+xRange/2
            joystick.y=(rudder.y-throttle.width)+yRange/2
        }

        //when moved, change the joystick's location and update the aileron and elevator values
        joystick.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    //save the joystick's previous location
                    xJoystick = event.x
                    yJoystick = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    //calculate the new location
                    val newY = joystick.y + (event.y - yJoystick)
                    val newX = joystick.x + (event.x - xJoystick)
                    //range variables
                    val startXRange = rudder.x + throttle.height
                    val startYRange = rudder.y - throttle.width
                    val endXRange = rudder.x + rudder.width - joystick.width
                    val endYRange = rudder.y - joystick.height
                    //if the new location is within the motion range
                    if ((newX in startXRange..endXRange) && (newY in startYRange..endYRange)) {
                        //set the joystick to the new location
                        joystick.x = newX
                        joystick.y = newY
                        //update the aileron and elevator values according to their range [-1...1]
                        val aileron = ((joystick.x - startXRange)/(endXRange-startXRange))*2-1
                        viewModel.setAileron(aileron.toDouble())
                        var elevator = ((joystick.y - startYRange)/(endYRange-startYRange))*2-1
                        viewModel.setElevator(elevator.toDouble())
                    }
                }
            }
            true
        })
    }

}