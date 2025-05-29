package org.example

import LogWriter
import java.io.File

fun main() {
    val outputStream = File("rrlogoutput.log").outputStream()

    val logger = LogWriter(outputStream)
    logger.write("TEST_ROOT_ARRAY",arrayOf("Root Array Contents"))
    logger.write("TEST_ONE_ARRAY_OBJECT", object { @JvmField val arrayProperty = arrayOf("Array Object Contents") } )
    logger.write("TEST_TWO_ARRAY_OBJECT", object {
        @JvmField val arrayProperty1 = arrayOf("Array Object Contents")
        @JvmField val arrayProperty2 = arrayOf("Array Object Contents")
    })
}