package com.plotva.jsondb.controller;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHtmlController {
    public OkHtmlController() {
    }

    OkHttpClient client = new OkHttpClient();

    public String run(HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (
                Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
