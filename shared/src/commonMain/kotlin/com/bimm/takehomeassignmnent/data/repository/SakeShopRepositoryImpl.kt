package com.bimm.takehomeassignmnent.data.repository

import com.bimm.takehomeassignmnent.data.source.SakeShopDataSource
import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository

class SakeShopRepositoryImpl(
    private val dataSource: SakeShopDataSource
) : SakeShopRepository {
    
    override suspend fun getAllSakeShops(): Result<List<SakeShop>> {
        return try {
            val shops = dataSource.getSakeShops()
            Result.success(shops)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getSakeShopById(id: String): Result<SakeShop?> {
        return try {
            val shops = dataSource.getSakeShops()
            val shop = shops.find { it.name == id }
            Result.success(shop)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}