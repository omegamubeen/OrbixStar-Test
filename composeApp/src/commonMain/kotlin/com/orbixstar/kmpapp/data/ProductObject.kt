package com.orbixstar.kmpapp.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductObject(
    val category: String,
    val location: String? = "Lahore University",
    val date: String? = "Today",
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)

@Serializable
data class Rating(
    val count: Int, val rate: Double
)