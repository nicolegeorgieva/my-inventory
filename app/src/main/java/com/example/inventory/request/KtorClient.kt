package com.example.inventory.request

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

val client = HttpClient {
    expectSuccess = false
}

// Sample usage
suspend fun fetchTodos(): HttpResponse {
    return client.get("https://jsonplaceholder.typicode.com/todos/1")
}