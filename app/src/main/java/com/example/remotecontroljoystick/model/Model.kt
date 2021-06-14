package com.example.remotecontroljoystick.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.PrintWriter
import java.lang.Thread.sleep
import java.net.Socket

object Model {
    private val m_ipAddress = MutableLiveData<String>()
    val ipAddress: LiveData<String>
        get() = m_ipAddress

    private val m_port = MutableLiveData<String>()
    val port: LiveData<String>
        get() = m_port

    fun connect() {
        Thread(Runnable {
            println("BEFORE")
            val host = m_ipAddress.toString()
        //    val port = Integer.parseInt(m_port.toString())
            val fg = Socket("192.168.1.14", 6400)
            println("AFTER")
            val out = PrintWriter(fg.getOutputStream(),true)
            val i=0
            while(i<10) {
                out.print("set/controls/flight/rudder -1 \r\n")
                out.flush()
                out.print("set/controls/flight/rudder 1 \r\n")
                out.flush()
                sleep(2)
            }
            println("END")
        }).start()
    }
}