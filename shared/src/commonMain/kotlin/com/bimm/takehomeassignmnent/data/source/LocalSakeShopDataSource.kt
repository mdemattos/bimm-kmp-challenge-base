package com.bimm.takehomeassignmnent.data.source

import com.bimm.takehomeassignmnent.domain.model.SakeShop
import kotlinx.serialization.json.Json

class LocalSakeShopDataSource(
    private val resourceReader: ResourceReader
) : SakeShopDataSource {
    
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    override suspend fun getSakeShops(): List<SakeShop> {
        val jsonContent = resourceReader.readText("sakeshop.json")
        return json.decodeFromString<List<SakeShop>>(jsonContent)
    }
}