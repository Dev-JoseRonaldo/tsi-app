package com.example.tsi_app;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private RecyclerView bookList;
    private BookAdapter adapter;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchInput = findViewById(R.id.searchInput);
        bookList = findViewById(R.id.bookList);
        Button searchButton = findViewById(R.id.searchButton);

        bookList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(new ArrayList<>());
        bookList.setAdapter(adapter);

        searchButton.setOnClickListener(v -> searchBooks(searchInput.getText().toString()));
    }

    private void searchBooks(String query) {
        String url = "https://openlibrary.org/search.json?q=" + query.replace(" ", "+");
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) return;
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    JSONArray docs = json.getJSONArray("docs");
                    ArrayList<Book> books = new ArrayList<>();

                    for (int i = 0; i < Math.min(20, docs.length()); i++) {
                        JSONObject item = docs.getJSONObject(i);
                        String title = item.optString("title");
                        String coverId = item.has("cover_i") ? item.getString("cover_i") : null;
                        String coverUrl = coverId != null ? "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg" : null;
                        String desc = item.optString("first_sentence");

                        books.add(new Book(title, coverUrl, desc));
                    }

                    runOnUiThread(() -> {
                        adapter = new BookAdapter(books);
                        bookList.setAdapter(adapter);
                    });
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }
}
