package com.fitness.config;

import com.google.gson.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class ApiSingleton {

    private static final String BASE_URL = "http://host.docker.internal:8080/projeto-desafio-fitness/api";

    private static ApiSingleton instance;
    private final HttpClient client;
    private final Gson gson;

    private static final ThreadLocal<String> tokenContext = new ThreadLocal<>();

    private ApiSingleton() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (data, tipo, ctx) ->
                        data != null ? new JsonPrimitive(data.toString()) : null)
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, tipo, ctx) ->
                        json != null && !json.getAsString().isBlank() ? LocalDateTime.parse(json.getAsString()) : null)
                .create();
    }

    public static synchronized ApiSingleton getInstance() {
        if (instance == null) {
            instance = new ApiSingleton();
        }
        return instance;
    }

    public static void setToken(String token) {
        tokenContext.set(token);
    }

    public static void clearToken() {
        tokenContext.remove();
    }

    public Gson getGson() {
        return this.gson;
    }

    private HttpRequest.Builder criarRequisicao(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json");

        String token = tokenContext.get();
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }

        return builder;
    }

    // ==========================================
    // MÉTODOS HTTP
    // ==========================================

    public HttpResponse<String> get(String endpoint) throws Exception {
        HttpRequest req = criarRequisicao(endpoint).GET().build();
        return this.client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> post(String endpoint, String jsonBody) throws Exception {
        HttpRequest req = criarRequisicao(endpoint)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody != null ? jsonBody : ""))
                .build();
        return this.client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> put(String endpoint, String jsonBody) throws Exception {
        HttpRequest req = criarRequisicao(endpoint)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody != null ? jsonBody : ""))
                .build();
        return this.client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(String endpoint) throws Exception {
        HttpRequest req = criarRequisicao(endpoint).DELETE().build();
        return this.client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
