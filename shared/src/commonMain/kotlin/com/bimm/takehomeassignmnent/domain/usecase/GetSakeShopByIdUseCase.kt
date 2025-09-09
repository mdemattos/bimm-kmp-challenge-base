package com.bimm.takehomeassignmnent.domain.usecase

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository

class GetSakeShopByIdUseCase(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(id: String): Result<SakeShop?> {
        return repository.getSakeShopById(id)
    }
}