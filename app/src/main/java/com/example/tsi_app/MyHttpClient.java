package com.example.tsi_app;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;

public class MyHttpClient {
    public static OkHttpClient createClient() {
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("openlibrary.org", "sha256/3qflTT6HO61kiKHBi+ZpZwG4OkARMSWxmdjg8YvuNBY=")
                .add("openlibrary.org", "sha256/K7rZOrXHknnsEhUH8nLL4MZkejquUuIvOIr6tCa0rbo=")
                .build();

        return new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build();
    }
}
