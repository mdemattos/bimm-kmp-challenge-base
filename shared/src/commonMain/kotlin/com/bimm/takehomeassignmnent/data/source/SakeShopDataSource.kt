package com.bimm.takehomeassignmnent.data.source

import com.bimm.takehomeassignmnent.domain.model.SakeShop

interface SakeShopDataSource {
    suspend fun getSakeShops(): List<SakeShop>
}