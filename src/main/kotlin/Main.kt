package org.example

import InspectionProperty
import LogWriter
import QrData
import QrExclude
import com.google.gson.*
import java.io.File
import java.lang.reflect.Type


val qrGson: Gson = makeQrGson()
fun makeQrGson(): Gson {
    val builder = GsonBuilder()
    val booleanSerializer: JsonSerializer<Boolean?> = object : JsonSerializer<Boolean?> {
         override fun serialize(
            b: Boolean?,
            type: Type,
            jsonSerializationContext: JsonSerializationContext
        ): JsonElement {
            // map booleans to [0,1] to save space
            return JsonPrimitive(if (b != null && b) 1 else 0)
        }
    }
    builder.registerTypeAdapter(Boolean::class.java, booleanSerializer)
    builder.registerTypeAdapter(Boolean::class.javaPrimitiveType, booleanSerializer)

    val booleanDeserializer = object : JsonDeserializer<Boolean?> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Boolean? {
            return json == JsonPrimitive(1)
        }
    }
    builder.registerTypeAdapter(Boolean::class.java, booleanDeserializer)
    builder.registerTypeAdapter(Boolean::class.javaPrimitiveType, booleanDeserializer)
    builder.addSerializationExclusionStrategy(
        object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                return fieldAttributes.getAnnotation<QrExclude?>(QrExclude::class.java) != null
            }

            override fun shouldSkipClass(aClass: Class<*>?): Boolean {
                return false
            }
        })

    val inspectionPropertyJsonSerializer: JsonSerializer<InspectionProperty?> =
        object : JsonSerializer<InspectionProperty?> {
            public override fun serialize(
                p: InspectionProperty?,
                type: Type?,
                jsonSerializationContext: JsonSerializationContext?
            ): JsonElement? {
                // Throw away everything but the boolean and then map it to [0,1] to save space
                return JsonPrimitive(if (p != null && p.valid) 1 else 0)
            }
        }
    builder.registerTypeAdapter(InspectionProperty::class.java, inspectionPropertyJsonSerializer)
    val inspectionPropertyJsonDeserializer = object : JsonDeserializer<InspectionProperty?> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): InspectionProperty? {
            return InspectionProperty(json == JsonPrimitive(1),0)
        }

    }
    builder.registerTypeAdapter(InspectionProperty::class.java, inspectionPropertyJsonDeserializer)

    return builder.create()
}



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

fun qrcodedatatorrlog() {
    val bufferedReader = File("extracteddata.txt").bufferedReader()

    val string = bufferedReader.use { it.readText() }

    println(string)

    val data = qrGson.fromJson(string, QrData::class.java)

    println(data)

    val outputStream = File("rrlogoutput.log").outputStream()

    val logger = LogWriter(outputStream)

    logger.write("INSPECTION_DATA", data)
}