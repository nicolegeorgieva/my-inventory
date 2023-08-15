package com.example.inventory.request

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

val client = HttpClient {
    expectSuccess = false
}

suspend fun fetchTodos(): HttpResponse {
    return client.get("http://0.0.0.0:8080")
}