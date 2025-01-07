package com.orbixstar.kmpapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface ProductStorage {
    suspend fun saveObjects(newObjects: List<ProductObject>)

    fun getObjectById(objectId: Int): Flow<ProductObject?>

    fun getObjects(): Flow<List<ProductObject>>
}

class InMemoryProductStorage : ProductStorage {
    private val storedObjects = MutableStateFlow(emptyList<ProductObject>())

    override suspend fun saveObjects(newObjects: List<ProductObject>) {
        storedObjects.value = newObjects
    }

    override fun getObjectById(objectId: Int): Flow<ProductObject?> {
        return storedObjects.map { objects ->
            objects.find { it.id == objectId }
        }
    }

    override fun getObjects(): Flow<List<ProductObject>> = storedObjects
}
