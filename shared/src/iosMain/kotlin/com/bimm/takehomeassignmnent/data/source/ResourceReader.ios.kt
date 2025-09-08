package com.bimm.takehomeassignmnent.data.source

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

@OptIn(ExperimentalForeignApi::class)
actual class ResourceReader {
    actual fun readText(fileName: String): String {
        val bundle = NSBundle.mainBundle
        val path = bundle.pathForResource(fileName.substringBeforeLast("."), ofType = fileName.substringAfterLast("."))
            ?: throw IllegalArgumentException("Resource $fileName not found")
        
        return NSString.stringWithContentsOfFile(
            path = path,
            encoding = NSUTF8StringEncoding,
            error = null
        )?.toString() ?: throw IllegalArgumentException("Could not read resource $fileName")
    }
}