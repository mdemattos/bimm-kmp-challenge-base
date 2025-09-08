package com.bimm.takehomeassignmnent.domain.repository

import com.bimm.takehomeassignmnent.domain.model.SakeShop

interface SakeShopRepository {
    suspend fun getAllSakeShops(): Result<List<SakeShop>>
    suspend fun getSakeShopById(id: String): Result<SakeShop?>
}