package com.orbixstar.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orbixstar.kmpapp.data.ProductObject
import com.orbixstar.kmpapp.data.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ListViewModel(productRepository: ProductRepository) : ViewModel() {
    val objects: StateFlow<List<ProductObject>> =
        productRepository.getObjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
