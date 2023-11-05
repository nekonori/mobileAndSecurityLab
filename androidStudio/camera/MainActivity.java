package com.example.exam;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.net.Uri;
import android.os.Bundle;

import com.example.exam.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        imageUri = createUri();
        registerPictureLauncher();

        mainBinding.btnTakePicture.setOnClickListener(view -> {
            takePictureLauncher.launch(imageUri);
        });
    }

    private Uri createUri() {
        File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        return FileProvider.getUriForFile(getApplicationContext(), "com.example.exam.fileProvider", imageFile);
    }

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                try {
                    if (o) {
                        mainBinding.ivUser.setImageURI(null);
                        mainBinding.ivUser.setImageURI(imageUri);
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });
    }
}