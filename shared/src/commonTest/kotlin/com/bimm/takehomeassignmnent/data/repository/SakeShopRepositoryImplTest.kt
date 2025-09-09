package com.bimm.takehomeassignmnent.data.repository

import com.bimm.takehomeassignmnent.data.source.SakeShopDataSource
import com.bimm.takehomeassignmnent.domain.model.SakeShop
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SakeShopRepositoryImplTest {

    private val testShops = listOf(
        SakeShop(
            name = "Sake Shop 1",
            description_ = "Description 1",
            picture = null,
            rating = 4.5,
            address = "Address 1",
            coordinates = listOf(36.0, 138.0),
            google_maps_link = "https://maps1.com",
            website = "https://shop1.com"
        ),
        SakeShop(
            name = "Sake Shop 2",
            description_ = "Description 2",
            picture = "https://image.jpg",
            rating = 4.0,
            address = "Address 2",
            coordinates = listOf(36.1, 138.1),
            google_maps_link = "https://maps2.com",
            website = "https://shop2.com"
        )
    )

    @Test
    fun `getAllSakeShops should return success with shops`() = runTest {
        // Given
        val dataSource = FakeSuccessDataSource(testShops)
        val repository = SakeShopRepositoryImpl(dataSource)

        // When
        val result = repository.getAllSakeShops()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(testShops, result.getOrNull())
    }

    @Test
    fun `getAllSakeShops should return failure when data source throws exception`() = runTest {
        // Given
        val dataSource = FakeFailureDataSource()
        val repository = SakeShopRepositoryImpl(dataSource)

        // When
        val result = repository.getAllSakeShops()

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `getSakeShopById should return success with shop when found`() = runTest {
        // Given
        val dataSource = FakeSuccessDataSource(testShops)
        val repository = SakeShopRepositoryImpl(dataSource)

        // When
        val result = repository.getSakeShopById("Sake Shop 1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(testShops[0], result.getOrNull())
    }

    @Test
    fun `getSakeShopById should return success with null when not found`() = runTest {
        // Given
        val dataSource = FakeSuccessDataSource(testShops)
        val repository = SakeShopRepositoryImpl(dataSource)

        // When
        val result = repository.getSakeShopById("Non-existent Shop")

        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `getSakeShopById should return failure when data source throws exception`() = runTest {
        // Given
        val dataSource = FakeFailureDataSource()
        val repository = SakeShopRepositoryImpl(dataSource)

        // When
        val result = repository.getSakeShopById("Any Shop")

        // Then
        assertTrue(result.isFailure)
    }

    private class FakeSuccessDataSource(private val shops: List<SakeShop>) : SakeShopDataSource {
        override suspend fun getSakeShops(): List<SakeShop> = shops
    }

    private class FakeFailureDataSource : SakeShopDataSource {
        override suspend fun getSakeShops(): List<SakeShop> {
            throw RuntimeException("Data source error")
        }
    }
}