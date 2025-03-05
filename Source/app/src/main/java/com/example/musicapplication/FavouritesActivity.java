package com.example.musicapplication;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapplication.FavouritesAdapter;
import com.example.musicapplication.R;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    ListView listViewFavorites;
    List<String> favoriteSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        listViewFavorites = findViewById(R.id.listView_favourites);

        favoriteSongs = getIntent().getStringArrayListExtra("favoriteSongs");

        FavouritesAdapter favoritesAdapter = new FavouritesAdapter(this, favoriteSongs);
        listViewFavorites.setAdapter(favoritesAdapter);
    }
}
