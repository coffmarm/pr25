package com.example.pr26;

import  android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mTurbSound, mSupSound, mBurnSound, mShotSound, mSopSound, mVertSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mTurbSound = loadSound("turb.ogg");
        mSupSound = loadSound("sup.ogg");
        mBurnSound = loadSound("burn.ogg");
        mShotSound = loadSound("shot.ogg");
        mSopSound = loadSound("sop.ogg");
        mVertSound = loadSound("vert.ogg");

        ImageButton turbImageButton = findViewById(R.id.image_turb);
        turbImageButton.setOnClickListener(onClickListener);

        ImageButton chickenImageButton = findViewById(R.id.image_sup);
        chickenImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = findViewById(R.id.image_burn);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = findViewById(R.id.image_shot);
        duckImageButton.setOnClickListener(onClickListener);

        ImageButton sheepImageButton = findViewById(R.id.image_sop);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageButton dogImageButton = findViewById(R.id.image_vert);
        dogImageButton.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_turb) {
                playSound(mTurbSound);
            } else if (v.getId() == R.id.image_sup) {
                playSound(mSupSound);
            } else if (v.getId() == R.id.image_burn) {
                playSound(mBurnSound);
            } else if (v.getId() == R.id.image_shot) {
                playSound(mShotSound);
            } else if (v.getId() == R.id.image_sop) {
                playSound(mSopSound);
            } else if (v.getId() == R.id.image_vert) {
                playSound(mVertSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mTurbSound = loadSound("turb.ogg");
        mSupSound = loadSound("sup.ogg");
        mBurnSound = loadSound("burn.ogg");
        mShotSound = loadSound("shot.ogg");
        mSopSound = loadSound("sop.ogg");
        mVertSound = loadSound("vert.ogg");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}