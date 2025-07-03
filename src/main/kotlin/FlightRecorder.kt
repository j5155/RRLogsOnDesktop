package com.acmerobotics.roadrunner.ftc

import LogWriter
import java.io.File
import java.io.OutputStream
import java.text.SimpleDateFormat

// there may be legacy logs here, but we should be robust to that
val LOG_ROOT = File("RoadRunner/logs");

private val DATE_FORMAT = SimpleDateFormat("yyyy_MM_dd__HH_mm_ss_SSS");

private fun openLogFile(suffix: String): OutputStream {
    val filename = "${DATE_FORMAT.format(System.currentTimeMillis())}__$suffix.log"
    val file = File(LOG_ROOT, filename)
    LOG_ROOT.mkdirs()
    return file.outputStream()
}

private class FlightLogWriter(suffix: String) {
    private val outputStream = openLogFile(suffix)
    private val writer = LogWriter(outputStream)

    fun write(ch: String, o: Any) {
        writer.write(ch, o)
    }

    fun close() {
        outputStream.flush() // paranoid flush
        outputStream.close()
    }
}

object FlightRecorder {
    private var writer: FlightLogWriter? = null


   fun onOpModePreInit() {
        synchronized(this) {
            writer?.close()
            writer = null

            // clean up old files
            run {
                val fs = LOG_ROOT.listFiles() ?: return@run
                fs.sortBy { it.lastModified() }
                var totalSizeBytes = fs.sumOf { it.length() }

                var i = 0
                while (i < fs.size && totalSizeBytes >= 250 * 1000 * 1000) {
                    totalSizeBytes -= fs[i].length()
                    if (!fs[i].delete()) {
                        // avoid panicking here
                        //RobotLog.setGlobalErrorMsg("Unable to delete file " + fs[i].absolutePath);
                    }
                    ++i
                }
            }


            writer = FlightLogWriter("UnknownOpMode")

            write("OPMODE_PRE_INIT", System.nanoTime())
        }
    }

    fun onOpModePreStart() {
        write("OPMODE_PRE_START", System.nanoTime())
    }

    fun onOpModePostStop() {
        synchronized(this) {
            write("OPMODE_POST_STOP", System.nanoTime())

            writer?.close()
            writer = null
        }
    }

    @JvmStatic
    fun write(ch: String, o: Any) {
        synchronized(this) {
            writer?.write(ch, o)
        }
    }
}

class DownsampledWriter(val channel: String, val maxPeriod: Long) {
    private var nextWriteTimestamp = 0L
    fun write(msg: Any) {
        val now = System.nanoTime()
        if (now >= nextWriteTimestamp) {
            nextWriteTimestamp = (now / maxPeriod + 1) * maxPeriod
            FlightRecorder.write(channel, msg)
        }
    }
}
