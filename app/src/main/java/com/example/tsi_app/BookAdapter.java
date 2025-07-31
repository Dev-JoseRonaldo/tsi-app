package com.example.tsi_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView, descView;
        public ImageView coverView;

        public BookViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.bookTitle);
            descView = view.findViewById(R.id.bookDesc);
            coverView = view.findViewById(R.id.bookCover);
        }
    }

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleView.setText(book.title);
        if (book.coverUrl != null) {
            holder.descView.setVisibility(View.GONE);
            holder.coverView.setVisibility(View.VISIBLE);
            Picasso.get().load(book.coverUrl).into(holder.coverView);
        } else {
            holder.coverView.setVisibility(View.GONE);
            holder.descView.setVisibility(View.VISIBLE);
            holder.descView.setText(book.description != null ? book.description : "Sem descrição.");
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
