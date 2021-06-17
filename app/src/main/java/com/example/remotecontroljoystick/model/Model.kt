package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object Model {
    private lateinit var out: PrintWriter
    var dispatchQueue: BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()

    /* Connect to flightGear simulator, with active object design pattern */
    fun connect(ip: String, port: Int) {
        //create new thread
        Thread(Runnable {
            val fg = Socket(ip, port)
            out = PrintWriter(fg.getOutputStream(),true)
            while(true) {
                try {
                    dispatchQueue.take().run()
                } catch (e: InterruptedException) { }
            }
        }).start()
    }

    /* When rudder changes, update the flightGear with the new rudder's value
        by adding a new runnable to the dispatchQueue */
    fun rudderChanged(rudder: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/rudder $rudder \r\n")
            out.flush()
        })
    }

    /* When throttle changes, update the flightGear with the new throttle's value
        by adding a new runnable to the dispatchQueue */
    fun throttleChanged(throttle: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/engines/current-engine/throttle $throttle \r\n")
            out.flush()
        })
    }

    /* When aileron changes, update the flightGear with the new aileron's value
        by adding a new runnable to the dispatchQueue */
    fun aileronChanged(aileron: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/aileron $aileron \r\n")
            out.flush()
        })
    }

    /* When elevator changes, update the flightGear with the new elevator's value
        by adding a new runnable to the dispatchQueue */
    fun elevatorChanged(elevator: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/elevator $elevator \r\n")
            out.flush()
        })
    }
}
