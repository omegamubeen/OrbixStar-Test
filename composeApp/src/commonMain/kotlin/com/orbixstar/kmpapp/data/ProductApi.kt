package com.orbixstar.kmpapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException

interface ProductApi {
    suspend fun getData(): List<ProductObject>
}

class KtorProductApi(private val client: HttpClient) : ProductApi {
    companion object {
        private const val API_URL =
            "https://fakestoreapi.com/products"
    }

    override suspend fun getData(): ArrayList<ProductObject> {
        return try {
            client.get(API_URL).body()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            ArrayList()
        }
    }
}
