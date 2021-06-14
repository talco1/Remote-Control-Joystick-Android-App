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
        //original thread - works
        /*
        Thread(Runnable {
            val fg = Socket(ip, port)
            out = PrintWriter(fg.getOutputStream(),true)
            while(true) {
                out.print("set /controls/flight/rudder -1 \r\n")
                out.flush()
                out.print("set /controls/flight/rudder 1 \r\n")
                out.flush()
            }
        }).start()
         */
        //active object
/*
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

 */
        //thread pool
        var executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(Runnable {
                val fg = Socket(ip, port)
                out = PrintWriter(fg.getOutputStream())
                val i=0
                while(true) {
                    out.print("set /controls/flight/rudder -1 \r\n")
                    out.flush()
                    out.print("set /controls/flight/rudder 1 \r\n")
                    out.flush()
                }
            })
        } catch (e: InterruptedException) { }
    }

    fun rudderChanged() {
        dispatchQueue.put(Runnable {
            out.print("set /controls/flight/rudder -1 \r\n")
            out.flush()
            out.print("set /controls/flight/rudder 1 \r\n")
            out.flush()
            out.print("set /controls/flight/aileron -1 \r\n")
            out.flush()
            out.print("set /controls/flight/aileron 1 \r\n")
            out.flush()
            out.print("set /controls/flight/elevator -1 \r\n")
            out.flush()
            out.print("set /controls/flight/elevator 1 \r\n")
            out.flush()
            out.print("set /controls/engines/current-engine/throttle 0 \r\n")
            out.flush()
            out.print("set /controls/engines/current-engine/throttle 1 \r\n")
            out.flush()
        })
    }
}

/*
                out.print("set /controls/flight/rudder -1 \r\n")
                out.flush()
                out.print("set /controls/flight/rudder 1 \r\n")
                out.flush()
                out.print("set /controls/flight/aileron -1 \r\n")
                out.flush()
                out.print("set /controls/flight/aileron 1 \r\n")
                out.flush()
                out.print("set /controls/flight/elevator -1 \r\n")
                out.flush()
                out.print("set /controls/flight/elevator 1 \r\n")
                out.flush()
                out.print("set /controls/engines/current-engine/throttle 0 \r\n")
                out.flush()
                out.print("set /controls/engines/current-engine/throttle 1 \r\n")
                out.flush()
 */