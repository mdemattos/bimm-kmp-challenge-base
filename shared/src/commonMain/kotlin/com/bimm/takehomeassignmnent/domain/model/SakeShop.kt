package com.bimm.takehomeassignmnent.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SakeShop(
    val name: String,
    val description: String,
    val picture: String?,
    val rating: Double,
    val address: String,
    val coordinates: List<Double>,
    val google_maps_link: String,
    val website: String
) {
    val latitude: Double get() = coordinates[0]
    val longitude: Double get() = coordinates[1]
}