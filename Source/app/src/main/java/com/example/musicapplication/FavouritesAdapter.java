package com.example.musicapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FavouritesAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> favoriteSongs;

    public FavouritesAdapter(Context context, List<String> favoriteSongs) {
        super(context, R.layout.favourite_list_item, favoriteSongs);
        this.context = context;
        this.favoriteSongs = favoriteSongs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listItemView = inflater.inflate(R.layout.favourite_list_item, parent, false);
        }

        String currentSong = favoriteSongs.get(position);

        TextView textViewFavoriteSong = listItemView.findViewById(R.id.textViewFavoriteSong);
        textViewFavoriteSong.setText(currentSong);

        return listItemView;
    }
}
