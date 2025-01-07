package com.orbixstar.kmpapp.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductRepository(
    private val productApi: ProductApi,
    private val productStorage: ProductStorage,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        productStorage.saveObjects(productApi.getData())
    }

    fun getObjects(): Flow<List<ProductObject>> = productStorage.getObjects()

    fun getObjectById(objectId: Int): Flow<ProductObject?> = productStorage.getObjectById(objectId)
}
