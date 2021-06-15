package com.example.remotecontroljoystick.model

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object Model {
    private lateinit var out: PrintWriter
    var dispatchQueue: BlockingQueue<Runnable> = LinkedBlockingQueue<Runnable>()

    fun connect(ip: String, port: Int) {
        Thread(Runnable {
            val fg = Socket(ip, port)
            out = PrintWriter(fg.getOutputStream(),true)
            while(true) {
                try {
                    dispatchQueue.take().run()
                } catch (e: InterruptedException) {
                    println("ERROR")
                }
            }
        }).start()
    }

    fun rudderChanged(rudder: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/rudder $rudder \r\n")
            out.flush()
        })
    }
    fun throttleChanged(throttle: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/engines/current-engine/throttle $throttle \r\n")
            out.flush()
        })
    }
    fun aileronChanged(aileron: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/aileron $aileron \r\n")
            out.flush()
        })
    }
    fun elevatorChanged(elevator: Double) {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/elevator $elevator \r\n")
            out.flush()
        })
    }
}
