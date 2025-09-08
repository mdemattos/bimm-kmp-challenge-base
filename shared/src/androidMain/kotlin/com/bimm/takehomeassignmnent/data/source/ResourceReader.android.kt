package com.bimm.takehomeassignmnent.data.source

import android.content.Context

actual class ResourceReader(private val context: Context) {
    actual fun readText(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { 
            it.readText() 
        }
    }
}