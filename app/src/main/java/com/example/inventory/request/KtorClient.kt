package com.example.inventory.request

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json

val client = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun fetchTodos(): HttpResponse {
    return client.get("http://0.0.0.0:8080")
}
