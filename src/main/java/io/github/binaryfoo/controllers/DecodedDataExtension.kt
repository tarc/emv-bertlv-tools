package io.github.binaryfoo.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.binaryfoo.DecodedData
import kotlin.Range

import java.util.HashMap
import kotlin.platform.platformStatic

public class DecodedDataExtension {

    class object {
        platformStatic public fun hexDumpPositionToJson(d: DecodedData): String {
            val mapper = ObjectMapper()
            val position = if (d.tlv == null) {
                mapOf("position" to toStartAndEnd(d.positionInHexDump))
            } else {
                mapOf(
                    "position" to toStartAndEnd(d.positionInHexDump),
                    "tag" to toStartAndEnd(d.tagPositionInHexDump!!, d.tlv.tag.hexString),
                    "length" to toStartAndEnd(d.lengthPositionInHexDump!!, d.tlv.length)
                )
            }
            try {
                return mapper.writeValueAsString(position)
            } catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
        }

        fun toStartAndEnd(range: Range<Int>): Map<String, Int> {
            return mapOf("start" to range.start, "end" to range.end)
        }

        fun toStartAndEnd(range: Range<Int>, value: Any): Map<String, Any> {
            return mapOf("start" to range.start, "end" to range.end, "value" to value.toString())
        }
    }
}
