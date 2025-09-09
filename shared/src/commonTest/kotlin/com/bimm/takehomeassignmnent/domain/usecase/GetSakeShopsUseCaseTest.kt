package com.bimm.takehomeassignmnent.domain.usecase

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetSakeShopsUseCaseTest {

    private val testShops = listOf(
        SakeShop(
            name = "Test Shop 1",
            description_ = "Test Description 1",
            picture = null,
            rating = 4.5,
            address = "Test Address 1",
            coordinates = listOf(36.0, 138.0),
            google_maps_link = "https://test.com",
            website = "https://test1.com"
        ),
        SakeShop(
            name = "Test Shop 2",
            description_ = "Test Description 2",
            picture = "https://test.jpg",
            rating = 4.0,
            address = "Test Address 2",
            coordinates = listOf(36.1, 138.1),
            google_maps_link = "https://test2.com",
            website = "https://test2.com"
        )
    )

    @Test
    fun `should return shops when repository returns success`() = runTest {
        // Given
        val repository = FakeSuccessRepository(testShops)
        val useCase = GetSakeShopsUseCase(repository)

        // When
        val result = useCase.invoke()

        // Then
        assertEquals(testShops, result)
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        // Given
        val repository = FakeFailureRepository()
        val useCase = GetSakeShopsUseCase(repository)

        // When & Then
        assertFailsWith<RuntimeException> {
            useCase.invoke()
        }
    }

    private class FakeSuccessRepository(private val shops: List<SakeShop>) : SakeShopRepository {
        override suspend fun getAllSakeShops(): Result<List<SakeShop>> {
            return Result.success(shops)
        }

        override suspend fun getSakeShopById(id: String): Result<SakeShop?> {
            return Result.success(shops.find { it.name == id })
        }
    }

    private class FakeFailureRepository : SakeShopRepository {
        override suspend fun getAllSakeShops(): Result<List<SakeShop>> {
            return Result.failure(RuntimeException("Test error"))
        }

        override suspend fun getSakeShopById(id: String): Result<SakeShop?> {
            return Result.failure(RuntimeException("Test error"))
        }
    }
}