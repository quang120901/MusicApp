package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity {
    ImageButton btnPlay, btnNext, btnPrevious, btnBack, btnFV, btnRewind, btnForward, btnTimer, btnModeListen;
    TextView txtName, txtStart, txtEnd;
    SeekBar seekMusic;
    String sName;
    ImageView imgViewPlayer;
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;
    Thread updateSeekBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnModeListen = findViewById(R.id.button_modelisten);
        btnTimer = findViewById(R.id.button_timer);
        btnForward = findViewById(R.id.button_forward);
        btnRewind = findViewById(R.id.button_rewind);
        btnFV = findViewById(R.id.button_favourite);
        btnBack = findViewById(R.id.button_backtolist);
        btnPlay = findViewById(R.id.button_play);
        btnNext = findViewById(R.id.button_next);
        btnPrevious = findViewById(R.id.button_previous);
        txtName = findViewById(R.id.textview_songnameplayer);
        txtStart = findViewById(R.id.textview_start);
        txtEnd = findViewById(R.id.textview_end);
        seekMusic = findViewById(R.id.seekbar_player);
        imgViewPlayer = findViewById(R.id.imageview_player);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("pos", 0);
        txtName.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sName = mySongs.get(position).getName();
        txtName.setText(sName);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPossition = 0;

                while (currentPossition < totalDuration) {
                    try {
                        sleep(500);
                        currentPossition = mediaPlayer.getCurrentPosition();
                        seekMusic.setProgress(currentPossition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        seekMusic.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();

        seekMusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        seekMusic.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        seekMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtEnd.setText(endTime);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtStart.setText(currentTime);
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        List<String> favouriteSongs = new ArrayList<>();
        final Boolean[] isFavourite = {false};
        btnFV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavourite[0]) {
                    btnFV.setBackgroundResource(R.drawable.ic_favorite);
                    favouriteSongs.add(sName);
                    isFavourite[0] = !isFavourite[0];
                } else {
                    btnFV.setBackgroundResource(R.drawable.ic_no_favorite);
                    favouriteSongs.remove(sName);
                    isFavourite[0] = !isFavourite[0];
                }

                Intent intent = new Intent(PlayerActivity.this, FavouritesActivity.class);
                intent.putStringArrayListExtra("favoriteSongs", (ArrayList<String>) favouriteSongs);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                } else {
                    btnPlay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                    startAnimation(imgViewPlayer);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (MainActivity.repeat == true && MainActivity.shuffle == false){
                    btnPlay.performClick();
                } else {
                    btnNext.performClick();
                }
            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sName = mySongs.get(position).getName();
                txtName.setText(sName);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);

                String endTime = createTime(mediaPlayer.getDuration());
                txtEnd.setText(endTime);

                seekMusic.setProgress(0);
                seekMusic.setMax(mediaPlayer.getDuration());

                startAnimation(imgViewPlayer);


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (MainActivity.repeat == true && MainActivity.shuffle == false){
                            btnPlay.performClick();
                        } else {
                            btnNext.performClick();
                        }
                    }
                });

                if(MainActivity.shuffle == true && MainActivity.repeat == false)
                {
                    position = getRandom(mySongs.size() - 1);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = (position + 1) % mySongs.size();
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sName = mySongs.get(position).getName();
                txtName.setText(sName);
                mediaPlayer.start();
                btnPlay.setBackgroundResource(R.drawable.ic_pause);

                String endTime = createTime(mediaPlayer.getDuration());
                txtEnd.setText(endTime);

                seekMusic.setProgress(0);
                seekMusic.setMax(mediaPlayer.getDuration());


                startAnimation(imgViewPlayer);


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (MainActivity.repeat == true && MainActivity.shuffle == false){
                            btnPlay.performClick();
                        } else {
                            btnNext.performClick();
                        }
                    }
                });

                if(MainActivity.shuffle == true && MainActivity.repeat == false)
                {
                    position = getRandom(mySongs.size() - 1);
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                }
            }
        });

        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                }
            }
        });

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        btnModeListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(MainActivity.repeat == false && MainActivity.shuffle == false)
                    {
                        MainActivity.repeat = true;
                        btnModeListen.setBackgroundResource(R.drawable.ic_repeat_on);
                    } else if(MainActivity.repeat == true)
                    {
                        MainActivity.shuffle = true;
                        MainActivity.repeat = false;
                        btnModeListen.setBackgroundResource(R.drawable.ic_shuffle_on);
                    } else if(MainActivity.shuffle == true && MainActivity.repeat == false)
                    {
                        MainActivity.shuffle = false;
                        btnModeListen.setBackgroundResource(R.drawable.ic_repeat_off);
                    }
            }
        });

        if(MainActivity.shuffle == true && MainActivity.repeat == false)
        {
            position = getRandom(mySongs.size() - 1);
        }
    }

    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i + 1);
    }

    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time += min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;
    }

    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgViewPlayer, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    public void showBottomSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(PlayerActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_dialog);
        dialog.show();
        dialog.findViewById(R.id.min_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Music will start after 1 minutes", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer_on);
                dialog.dismiss();
//                mediaPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.performClick();
                        btnTimer.setBackgroundResource(R.drawable.ic_timer);
                    }
                },60000);
            }
        });
        dialog.findViewById(R.id.min_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Music will start after 5 minutes", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer_on);
                dialog.dismiss();
                mediaPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.performClick();
                        btnTimer.setBackgroundResource(R.drawable.ic_timer);
                    }
                },5*60000);
            }
        });
        dialog.findViewById(R.id.min_15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Music will start after 15 minutes", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer_on);
                dialog.dismiss();
                mediaPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.performClick();
                        btnTimer.setBackgroundResource(R.drawable.ic_timer);
                    }
                },15*60000);
            }
        });
        dialog.findViewById(R.id.min_30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Music will start after 30 minutes", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer_on);
                dialog.dismiss();
                mediaPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.performClick();
                        btnTimer.setBackgroundResource(R.drawable.ic_timer);
                    }
                },30*60000);
            }
        });
        dialog.findViewById(R.id.min_60).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Music will start after 60 minutes", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer_on);
                dialog.dismiss();
                mediaPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.ic_play);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPlay.performClick();
                        btnTimer.setBackgroundResource(R.drawable.ic_timer);
                    }
                },60*60000);
            }
        });
        dialog.findViewById(R.id.min_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Timer is disabled", Toast.LENGTH_SHORT).show();
                btnTimer.setBackgroundResource(R.drawable.ic_timer);
                dialog.dismiss();
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                btnPlay.setBackgroundResource(R.drawable.ic_play);
            }
        });
    }
}