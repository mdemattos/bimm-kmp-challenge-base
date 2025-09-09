package com.bimm.takehomeassignmnent.data.source

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalSakeShopDataSourceTest {

    private val testJson = """
        [
            {
                "name": "Test Shop 1",
                "description": "Test Description 1",
                "picture": null,
                "rating": 4.5,
                "address": "Test Address 1",
                "coordinates": [36.0, 138.0],
                "google_maps_link": "https://maps1.com",
                "website": "https://shop1.com"
            },
            {
                "name": "Test Shop 2",
                "description": "Test Description 2",
                "picture": "https://image.jpg",
                "rating": 4.0,
                "address": "Test Address 2",
                "coordinates": [36.1, 138.1],
                "google_maps_link": "https://maps2.com",
                "website": "https://shop2.com"
            }
        ]
    """.trimIndent()

    @Test
    fun `getSakeShops should parse JSON and return shops list`() = runTest {
        // Given
        val resourceReader = FakeResourceReader(testJson)
        val dataSource = LocalSakeShopDataSource(resourceReader)

        // When
        val result = dataSource.getSakeShops()

        // Then
        assertEquals(2, result.size)
        assertEquals("Test Shop 1", result[0].name)
        assertEquals("Test Description 1", result[0].description_)
        assertEquals(4.5, result[0].rating)
        assertEquals("Test Shop 2", result[1].name)
        assertEquals("Test Description 2", result[1].description_)
        assertEquals(4.0, result[1].rating)
    }

    @Test
    fun `getSakeShops should throw exception when JSON is invalid`() = runTest {
        // Given
        val invalidJson = "{ invalid json }"
        val resourceReader = FakeResourceReader(invalidJson)
        val dataSource = LocalSakeShopDataSource(resourceReader)

        // When & Then
        assertFailsWith<Exception> {
            dataSource.getSakeShops()
        }
    }

    @Test
    fun `getSakeShops should throw exception when resource reader fails`() = runTest {
        // Given
        val resourceReader = FakeFailureResourceReader()
        val dataSource = LocalSakeShopDataSource(resourceReader)

        // When & Then
        assertFailsWith<RuntimeException> {
            dataSource.getSakeShops()
        }
    }

    private class FakeResourceReader(private val content: String) : ResourceReader {
        override fun readText(fileName: String): String = content
    }

    private class FakeFailureResourceReader : ResourceReader {
        override fun readText(fileName: String): String {
            throw RuntimeException("Failed to read resource")
        }
    }
}