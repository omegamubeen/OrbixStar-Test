package com.orbixstar.kmpapp.screens.detail

import androidx.lifecycle.ViewModel
import com.orbixstar.kmpapp.data.ProductObject
import com.orbixstar.kmpapp.data.ProductRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    fun getObject(objectId: Int): Flow<ProductObject?> =
        productRepository.getObjectById(objectId)
}
