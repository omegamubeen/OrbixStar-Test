package com.orbixstar.kmpapp.di

import com.orbixstar.kmpapp.data.InMemoryProductStorage
import com.orbixstar.kmpapp.data.KtorProductApi
import com.orbixstar.kmpapp.data.ProductApi
import com.orbixstar.kmpapp.data.ProductRepository
import com.orbixstar.kmpapp.data.ProductStorage
import com.orbixstar.kmpapp.screens.detail.DetailViewModel
import com.orbixstar.kmpapp.screens.list.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 30_000
            }

            install(HttpCache)
        }
    }

    single<ProductApi> { KtorProductApi(get()) }
    single<ProductStorage> { InMemoryProductStorage() }
    single {
        ProductRepository(get(), get()).apply {
            initialize()
        }
    }
}

val viewModelModule = module {
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
