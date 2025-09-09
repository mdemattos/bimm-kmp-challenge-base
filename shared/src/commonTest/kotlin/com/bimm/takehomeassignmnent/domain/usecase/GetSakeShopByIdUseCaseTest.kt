package com.bimm.takehomeassignmnent.domain.usecase

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class GetSakeShopByIdUseCaseTest {

    private val testShop = SakeShop(
        name = "Test Shop",
        description_ = "Test Description",
        picture = null,
        rating = 4.5,
        address = "Test Address",
        coordinates = listOf(36.0, 138.0),
        google_maps_link = "https://test.com",
        website = "https://test.com"
    )

    @Test
    fun `should return shop when found by id`() = runTest {
        // Given
        val repository = FakeRepository(testShop)
        val useCase = GetSakeShopByIdUseCase(repository)

        // When
        val result = useCase.invoke("Test Shop")

        // Then
        assertEquals(testShop, result)
    }

    @Test
    fun `should return null when shop not found`() = runTest {
        // Given
        val repository = FakeRepository(null)
        val useCase = GetSakeShopByIdUseCase(repository)

        // When
        val result = useCase.invoke("Non-existent Shop")

        // Then
        assertNull(result)
    }

    @Test
    fun `should throw exception when repository fails`() = runTest {
        // Given
        val repository = FakeFailureRepository()
        val useCase = GetSakeShopByIdUseCase(repository)

        // When & Then
        assertFailsWith<RuntimeException> {
            useCase.invoke("Test Shop")
        }
    }

    private class FakeRepository(private val shop: SakeShop?) : SakeShopRepository {
        override suspend fun getAllSakeShops(): Result<List<SakeShop>> {
            return Result.success(emptyList())
        }

        override suspend fun getSakeShopById(id: String): Result<SakeShop?> {
            return Result.success(shop)
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