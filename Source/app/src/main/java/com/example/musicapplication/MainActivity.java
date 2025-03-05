package com.example.musicapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] items;
    Button btnFavouritesListSong;


    static boolean shuffle = false, repeat = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview_song);
        btnFavouritesListSong = findViewById(R.id.button_favoritelistsongs);

        rumtimePermission();


//        btnFavouritesListSong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    public void rumtimePermission()
    {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                displaySongs();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }



    public ArrayList<File> findSong(File file)
    {
        ArrayList arrayList = new ArrayList();
        File [] files = file.listFiles();
        if(files != null)
        {
            for (File singlefile : files)
            {
                if (singlefile.isDirectory() && !singlefile.isHidden())
                {
                    arrayList.addAll(findSong(singlefile));
                } else
                {
                    if (singlefile.getName().endsWith(".wav"))
                    {
                        arrayList.add(singlefile);
                    }
                    else if (singlefile.getName().endsWith(".mp3"))
                    {
                        arrayList.add(singlefile);
                    }
                    else if (singlefile.getName().endsWith(".flac"))
                    {
                        arrayList.add(singlefile);
                    }
                }
            }
        }
        return arrayList;
    }

    void displaySongs()
    {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];

        for (int i = 0; i<mySongs.size(); i++)
        {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","").replace(".flac","");

        }

        customAdapter customAdapter = new customAdapter();
        customAdapter.setMySongs(mySongs);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) listView.getItemAtPosition(i);
                Boolean isFavorite = false;

                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                intent.putExtra("songs", mySongs);
                intent.putExtra("songname", songName);
                intent.putExtra("pos", i);
                intent.putExtra("isFavorite", isFavorite);

                startActivity(intent);
            }
        });
    }

    class customAdapter extends BaseAdapter
    {

        private ArrayList<File> mySongs;

        public void setMySongs(ArrayList<File> mySongs) {
            this.mySongs = mySongs;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View myView = getLayoutInflater().inflate(R.layout.list_items, null);
            TextView textsong = myView.findViewById(R.id.textview_songname);
            textsong.setSelected(true);
            textsong.setText(items[i]);

            return myView;
        }
    }

}