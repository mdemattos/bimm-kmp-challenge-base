package com.bimm.takehomeassignmnent.domain.usecase

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository

class GetSakeShopsUseCase(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(): List<SakeShop> {
        return repository.getAllSakeShops().getOrThrow()
    }
}