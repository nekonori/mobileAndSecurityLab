package com.example.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View squareView = findViewById(R.id.square_view);

        View animateButton = findViewById(R.id.animate_button);

        animateButton.setOnClickListener(e -> {
            ObjectAnimator animation = ObjectAnimator.ofFloat(squareView, "translationX", -200f, 200f);
            animation.setDuration(1000);
            animation.setRepeatCount(1);
            ObjectAnimator animation2 = ObjectAnimator.ofFloat(squareView, "translationY", -200f, 200f);
            animation2.setDuration(1000);
            animation2.setRepeatCount(1);
            squareView.setBackgroundColor(0xFF00FF00);
            animation.start();
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                    Toast.makeText(MainActivity.this, "started", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {
                    Toast.makeText(MainActivity.this, "ended", Toast.LENGTH_SHORT).show();
                    animation2.start();
                    squareView.setBackgroundColor(0xFF0000FF);
                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {

                }
            });
        });
    }
}