package com.bimm.takehomeassignmnent.data.source

expect class ResourceReader() {
    fun readText(fileName: String): String
}