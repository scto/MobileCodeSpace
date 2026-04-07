package com.mcs.core.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

object JsonUtils {
    val jsonConfig = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }
    inline fun <reified T> toJson(data: T): String {
        return try { jsonConfig.encodeToString(data) } catch (e: Exception) { e.printStackTrace(); "" }
    }
    inline fun <reified T> fromJson(json: String): T? {
        return try { jsonConfig.decodeFromString<T>(json) } catch (e: Exception) { e.printStackTrace(); null }
    }
}
