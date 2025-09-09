package com.bimm.takehomeassignmnent.data.source

import android.content.Context

actual class ResourceReader(private val context: Context) {
    actual fun readText(fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { 
                it.readText() 
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to read file: $fileName. Error: ${e.message}", e)
        }
    }
}